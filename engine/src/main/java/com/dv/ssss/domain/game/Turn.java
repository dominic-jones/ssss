package com.dv.ssss.domain.game;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.qi4j.api.injection.scope.This;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.property.Property;

@Mixins(Turn.TurnMixin.class)
public interface Turn {

    LocalDate currentDate();

    void endTurn();

    int number();

    class TurnMixin implements Turn {

        @This
        TurnState state;

        private Period elapsedTime() {

            int integer = number();

            return Period.years(integer);
        }

        @Override
        public void endTurn() {

            state.turn().set(
                    state.turn()
                         .get() + 1
            );
        }

        @Override
        public int number() {

            return state.turn()
                        .get();
        }

        @Override
        public LocalDate currentDate() {

            return state.startingDate().get().withPeriodAdded(elapsedTime(), 1);
        }
    }

    interface TurnState {

        Property<LocalDate> startingDate();

        Property<Integer> turn();
    }
}
