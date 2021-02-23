package halfbyte.game.rogue.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import halfbyte.game.rogue.common.Util;
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
    }

    private void onNetworkConnected(Connection connection){
        // make a new thread to play out a battle and send it back
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // make a tilemap
                    Tilemap tilemap = new Tilemap(15, 15);

                    // make some entities
                    EntityManager em = new EntityManager();
                    for (int i = 0; i < 5; ++i) {
                        em.addEntity(new Entity(Entity.EType.getRandom(),
                                Util.getRandomIntInRange(1, tilemap.getWidthInTiles() - 2),
                                Util.getRandomIntInRange(1, tilemap.getHeightInTiles() - 2)
                        ));
                    }

                    // send the tilemap ane entities
                    connection.sendTCP(tilemap);
                    connection.sendTCP(em);

                    // do full battle simulation and then send back all the actions that took place
                    // todo

                    // done
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
}
