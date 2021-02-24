package halfbyte.game.rogue.client;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

import java.util.LinkedList;
import java.util.Queue;

public class ScrollingCombatText extends Group {
    // variables
    private static Queue<ScrollingCombatText> s_ready = new LinkedList<>();
    private Label m_label;

    // methods
    public static ScrollingCombatText create(AssetManager am, String text, Color color, float x, float y, float distance, float duration){
        // get the head of the ready queue
        ScrollingCombatText sct = ScrollingCombatText.s_ready.poll();
        if (sct == null){
            // there wasnt an item so make a new one
            sct = new ScrollingCombatText(am);
        }
        else{
            int yy = 0;
        }
        sct.init(text, color, x, y, distance, duration);
        return sct;
    }

    private ScrollingCombatText(AssetManager am){
        // create the label
        this.m_label = new Label("", am.get("ui/skin.json", Skin.class));

        // add it
        this.addActor(this.m_label);
    }

    private void init(String text, Color color, float x, float y, float distance, float duration) {
        // clear any current actions
        this.clearActions();

        // text
        this.m_label.setText(text);

        // text color
        this.m_label.setColor(color);

        // pack label to lay it out
        this.m_label.pack();

        // size this based on size of label
        this.setSize(this.m_label.getWidth(), this.m_label.getHeight());

        // color this
        this.setColor(Color.WHITE);

        // position this
        this.setPosition(x, y, Align.center);

        // create the action to move/fade and then remove
        this.addAction(Actions.sequence(
                Actions.parallel(
                        Actions.fadeOut(duration),
                        Actions.moveBy(0.0f, distance, duration)
                ),
                new Action() {
                    @Override
                    public boolean act(float delta) {
                        // handle to this actor
                        ScrollingCombatText sct = (ScrollingCombatText) this.getActor();

                        // remove it from the stage
                        sct.remove();

                        // add it back into the ready queue
                        ScrollingCombatText.s_ready.add(sct);

                        // action complete
                        return true;
                    }
                }
        ));
    }
}
