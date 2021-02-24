package halfbyte.game.rogue.client.entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;

import halfbyte.game.rogue.client.Constants;
import halfbyte.game.rogue.client.TextProgressBar;
import halfbyte.game.rogue.client.tileset.TilesetForEntity;
import halfbyte.game.rogue.common.entity.Entity;

public class EntityRenderer extends Group {
    // variables
    private Entity m_entity;
    private TilesetForEntity m_tileset;
    private TextProgressBar m_hp_bar;

    // methods
    public EntityRenderer(AssetManager am, Entity entity, TilesetForEntity tileset){
        // save
        this.m_entity = entity;
        this.m_tileset = tileset;

        // size
        this.setSize(Constants.TILE_SIZE, Constants.TILE_SIZE);

        // position
        this.setPosition(this.m_entity.getTileX() * Constants.TILE_SIZE, this.m_entity.getTileY() * Constants.TILE_SIZE);

        // image
        Image image = new Image(this.m_tileset.getTextureRegion(this.m_entity.getType()));
        image.setSize(this.getWidth(), this.getHeight());
        this.addActor(image);

        // hp
        this.m_hp_bar = new TextProgressBar(am, this.getWidth() * 0.85f, 12.0f);
        this.m_hp_bar.setPosition((this.getWidth() - this.m_hp_bar.getWidth()) / 2, this.getHeight() - this.m_hp_bar.getHeight());
        this.m_hp_bar.setTextEnabled(false);
        this.addActor(this.m_hp_bar);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        this.m_hp_bar.setMaxValue(this.m_entity.getHpMax());
        this.m_hp_bar.setCurrentValue(this.m_entity.getHpCurrent());
        super.draw(batch, parentAlpha);
    }
}
