package com.dv.ssss.ui.player;

import com.dv.ssss.ui.View;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.qi4j.api.mixin.Mixins;

@Mixins(PlayerView.PlayerViewMixin.class)
public interface PlayerView extends View {

    class PlayerViewMixin implements PlayerView {

        HBox layout = layout();
        StringProperty playerName;

        @Override
        public Parent getView() {

            return layout;
        }

        HBox layout() {

            layout = new HBox();
            layout.setPadding(new Insets(10, 10, 10, 10));

            Text playerNameField = new Text();
            playerName = playerNameField.textProperty();

            layout.getChildren().add(playerNameField);

            return layout;
        }
    }
}
