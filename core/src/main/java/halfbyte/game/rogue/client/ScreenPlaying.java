package halfbyte.game.rogue.client;


import com.badlogic.gdx.utils.Align;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import halfbyte.game.rogue.client.entity.EntityManagerRenderer;
import halfbyte.game.rogue.client.tilemap.TilemapRenderer;
import halfbyte.game.rogue.client.tileset.TilesetForEntity;
import halfbyte.game.rogue.client.tileset.TilesetForTilemap;
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
                    }
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    private void onNetworkReceived(Tilemap tilemap){
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
        // remove current
        if (this.m_entity_manager_renderer != null){
            this.m_entity_manager_renderer.remove();
        }

        // make the new renderer
        this.m_entity_manager_renderer = new EntityManagerRenderer(entity_manager, this.m_entity_tileset);
        this.m_entity_manager_renderer.setPosition(this.m_map_renderer.getX(), this.m_map_renderer.getY());
        this.m_stage.addActor(this.m_entity_manager_renderer);
    }
}
