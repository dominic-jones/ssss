package com.dv.ssss.ui.player;

import com.dv.ssss.ui.View;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.qi4j.api.mixin.Mixins;

@Mixins(PlayerView.PlayerViewMixin.class)
public interface PlayerView extends View {

    void setPlayer(PlayerDto person);

    class PlayerViewMixin implements PlayerView {

        Text playerNameField = new Text();
        HBox layout = layout();

        @Override
        public Parent getView() {

            return layout;
        }

        HBox layout() {

            layout = new HBox();
            layout.setPadding(new Insets(10, 10, 10, 10));


            layout.getChildren().addAll(
                    playerNameField
            );

            return layout;
        }

        @Override
        public void setPlayer(PlayerDto person) {

            playerNameField.setText(person.getName());
        }
    }
}
