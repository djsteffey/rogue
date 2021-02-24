package halfbyte.game.rogue.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.util.ArrayList;
import java.util.List;

import halfbyte.game.rogue.common.ability.Ability;
import halfbyte.game.rogue.common.ability.AbilityHeal;
import halfbyte.game.rogue.common.ability.AbilityMeleeAttack;
import halfbyte.game.rogue.common.battleaction.BattleAction;
import halfbyte.game.rogue.common.Util;
import halfbyte.game.rogue.common.battleaction.BattleActionEntityMove;
import halfbyte.game.rogue.common.entity.Entity;
import halfbyte.game.rogue.common.entity.EntityManager;
import halfbyte.game.rogue.common.network.Network;
import halfbyte.game.rogue.common.tilemap.Tilemap;

public class ServerRogue {
    // variables
    private boolean m_running;
    private Thread m_thread;
    private Server m_server;

    //methods
    public ServerRogue(){
        this.m_running = false;
        this.m_server = null;
    }

    public void runAsync(){
        // check if already running
        if (this.m_running){
            return;
        }

        // flag as running
        this.m_running = true;

        // start thread
        this.m_thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ServerRogue.this.run();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        this.m_thread.start();
    }

    public void stop(){
        // check if not running
        if (this.m_running == false){
            return;
        }

        // flag as not running
        this.m_running = false;

        // wait for thread
        try {
            this.m_thread.join();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void run() throws Exception{
        // start kryo server
        this.m_server = new Server(Network.BUFFER_SIZES, Network.BUFFER_SIZES){
            @Override
            protected Connection newConnection() {
                return super.newConnection();
            }
        };
        Network.register(this.m_server.getKryo());
        this.m_server.start();
        this.m_server.bind(Network.SERVER_PORT);
        this.m_server.addListener(new Listener(){
            @Override
            public void connected(Connection connection) {
                ServerRogue.this.onNetworkConnected(connection);
            }

            @Override
            public void disconnected(Connection connection) {
                ServerRogue.this.onNetworkDisconnected(connection);
            }

            @Override
            public void received(Connection connection, Object object) {
                ServerRogue.this.onNetworkReceived(connection, object);
            }
        });

        // loop while running
        while (this.m_running){
            // nothing really to do here
            Thread.sleep(250);
        }

        // stop the server
        this.m_server.stop();
    }

    private void onNetworkConnected(Connection connection){
        // make a new thread to play out a battle and send it back
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // make a tilemap
                    Tilemap tilemap = new Tilemap(7, 7);

                    // make some entities
                    EntityManager em = new EntityManager();
                    for (int i = 0; i < 5; ++i) {
                        Entity entity = new Entity(Entity.EType.getRandom(),
                                Util.getRandomIntInRange(1, tilemap.getWidthInTiles() - 2),
                                Util.getRandomIntInRange(1, tilemap.getHeightInTiles() - 2)
                        );
                        entity.getAbilities().add(new AbilityMeleeAttack());
                        entity.getAbilities().add(new AbilityHeal());
                        em.addEntity(entity);
                    }

                    // send the tilemap ane entities
                    connection.sendTCP(tilemap);
                    connection.sendTCP(em);

                    // do full battle simulation and then send back all the actions that took place
                    List<BattleAction> battle_actions = ServerRogue.this.resolveBattle(tilemap, em);
                    connection.sendTCP(battle_actions);

                    // done...wait a couple second then close the connection
                    Thread.sleep(2000);
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void onNetworkDisconnected(Connection connection){
        // nothing to do
    }

    private void onNetworkReceived(Connection connection, Object object){
        // nothing to do
    }

    private List<BattleAction> resolveBattle(Tilemap tilemap, EntityManager entity_manager){
        // max 100 moves
        List<BattleAction> list = new ArrayList<>();
        for (int i = 0; i < 256; ++i){
            // get entity with the turn
            Entity entity = entity_manager.getEntityTurn();

            // go through their abilities and build possibilities
            List<Ability.AbilityTargetSet> sets = new ArrayList<>();
            for (Ability ability : entity.getAbilities()){
                // reduce their cooldowns at the same time
                ability.decrementCooldown();

                // now get their targets
                sets.addAll(ability.getTargetSets(entity, tilemap, entity_manager));
            }

            // if we got something then do one of them
            if (sets.isEmpty()){
                // move
                Util.EDirection direction = Util.EDirection.getRandom();

                // move local entity
                entity.moveDirection(direction);

                // create action for it
                list.add(new BattleActionEntityMove(entity.getId(), direction));

                // update initiative
                entity.resetInitiative();
            }
            else{
                // pick a set and execute it
                Ability.AbilityTargetSet ats = sets.get(Util.getRandomIntInRange(0, sets.size() - 1));
                List<BattleAction> actions = ats.ability.execute(entity, ats.target_entity, null);
                list.addAll(actions);

                // update initiative
                entity.resetInitiative();
            }
        }
        return list;
    }
}
