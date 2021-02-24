package halfbyte.game.rogue.client;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import java.util.ArrayList;
import java.util.List;
import halfbyte.game.rogue.client.entity.EntityManagerRenderer;
import halfbyte.game.rogue.client.tilemap.TilemapRenderer;
import halfbyte.game.rogue.client.tileset.TilesetForEntity;
import halfbyte.game.rogue.client.tileset.TilesetForTilemap;
import halfbyte.game.rogue.common.battleaction.BattleAction;
import halfbyte.game.rogue.common.battleaction.BattleActionEntityHeal;
import halfbyte.game.rogue.common.battleaction.BattleActionEntityMeleeAttack;
import halfbyte.game.rogue.common.battleaction.BattleActionEntityMove;
import halfbyte.game.rogue.common.entity.EntityManager;
import halfbyte.game.rogue.common.network.Network;
import halfbyte.game.rogue.common.tilemap.Tilemap;

public class ScreenPlaying extends ScreenAbstract {
    // variables
    private TilesetForTilemap m_map_tileset;
    private TilesetForEntity m_entity_tileset;
    private TilemapRenderer m_map_renderer;
    private EntityManagerRenderer m_entity_manager_renderer;
    private Client m_kryo_client;
    private final List<BattleAction> m_battle_actions = new ArrayList<>();

    // methods
    public ScreenPlaying(IGameServices game_services){
        super(game_services);

        // tiles
        this.m_map_tileset = new TilesetForTilemap(this.m_game_services.getAssetManager());
        this.m_entity_tileset = new TilesetForEntity(this.m_game_services.getAssetManager());

        // connect
        try {
            this.m_kryo_client = new Client(Network.BUFFER_SIZES, Network.BUFFER_SIZES);
            Network.register(this.m_kryo_client.getKryo());
            this.m_kryo_client.start();
            this.m_kryo_client.connect(250, "localhost", Network.SERVER_PORT);
            this.m_kryo_client.addListener(new Listener() {
                @Override
                public void connected(Connection connection) {
                    super.connected(connection);
                }

                @Override
                public void disconnected(Connection connection) {
                    super.disconnected(connection);
                }

                @Override
                public void received(Connection connection, Object object) {
                    // right now only receiving the tilemap and then entity manager
                    if (object instanceof Tilemap) {
                        ScreenPlaying.this.onNetworkReceived((Tilemap)object);
                    } else if (object instanceof EntityManager) {
                        ScreenPlaying.this.onNetworkReceived((EntityManager)object);
                    } else if (object instanceof List){
                        ScreenPlaying.this.onNetworkReceived((List)object);
                    }
                    else{
                        Gdx.app.log("Unknown Network Object", object.toString());
                    }
                }
            });

            // action to run battle turns
            this.m_stage.addAction(Actions.forever(Actions.sequence(
                    Actions.delay(0.75f),
                    new Action() {
                        @Override
                        public boolean act(float delta) {
                            ScreenPlaying.this.doBattleAction();
                            return true;
                        }
                    }
            )));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void onNetworkReceived(Tilemap tilemap){
        // received the tilemap for the battle
        // remove current map renderer if there is one
        if (this.m_map_renderer != null){
            this.m_map_renderer.remove();
        }

        // make the new map renderer
        this.m_map_renderer = new TilemapRenderer(tilemap, this.m_map_tileset);
        this.m_map_renderer.setPosition(Constants.RESOLUTION_WIDTH / 2,Constants.RESOLUTION_HEIGHT / 2, Align.center);
        this.m_stage.addActor(this.m_map_renderer);
    }

    private void onNetworkReceived(EntityManager entity_manager){
        // remove the entities for the battle
        // remove current entity renderer if there is one
        if (this.m_entity_manager_renderer != null){
            this.m_entity_manager_renderer.remove();
        }

        // make the new renderer
        this.m_entity_manager_renderer = new EntityManagerRenderer(this.m_game_services.getAssetManager(), entity_manager, this.m_entity_tileset);
        this.m_entity_manager_renderer.setPosition(this.m_map_renderer.getX(), this.m_map_renderer.getY());
        this.m_stage.addActor(this.m_entity_manager_renderer);
    }

    private void onNetworkReceived(List list){
        // received a list
        for (Object object : list){
            // check which object is in the list
            if (object instanceof BattleAction){
                // its a battle action
                this.onNetworkReceived((BattleAction)object);
            }
            else{
                Gdx.app.log("Unknown Network Object", object.toString());
            }
        }
    }

    private void onNetworkReceived(BattleAction action){
        // add to the list of battle actions
        synchronized (this.m_battle_actions){
            this.m_battle_actions.add(action);
        }
    }

    private void doBattleAction(){
        // do one battle action
        BattleAction action = null;
        synchronized (this.m_battle_actions){
            if (this.m_battle_actions.isEmpty() == false){
                action = this.m_battle_actions.get(0);
                this.m_battle_actions.remove(0);
            }
        }
        if (action != null){
            if (action instanceof BattleActionEntityMove){
                this.doBattleActionEntityMove((BattleActionEntityMove)action);
            }
            else if (action instanceof BattleActionEntityMeleeAttack){
                this.doBattleActionEntityMeleeAttack((BattleActionEntityMeleeAttack)action);
            }
            else if (action instanceof BattleActionEntityHeal){
                this.doBattleActionEntityHeal((BattleActionEntityHeal)action);
            }
            else{
                Gdx.app.log("Unknown Network Object", action.toString());
            }
        }
    }

    private void doBattleActionEntityMove(BattleActionEntityMove action){
        BattleActionExecutor.execute(
                this.m_game_services.getAssetManager(),
                this.m_map_renderer,
                this.m_entity_manager_renderer,
                action,
                0.5f
        );
    }

    private void doBattleActionEntityMeleeAttack(BattleActionEntityMeleeAttack action){
        BattleActionExecutor.execute(
                this.m_game_services.getAssetManager(),
                this.m_map_renderer,
                this.m_entity_manager_renderer,
                action,
                0.5f
        );
    }

    private void doBattleActionEntityHeal(BattleActionEntityHeal action){
        BattleActionExecutor.execute(
                this.m_game_services.getAssetManager(),
                this.m_map_renderer,
                this.m_entity_manager_renderer,
                action,
                0.5f
        );
    }
}
