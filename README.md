ssss
====
* Consider how to use properties on external role interfaces

Notes
* Domain objects have no setters or getters. Just their domain concepts.
* Domain objects should not leak outside of their layer. Use Dtos.

* ill inclined to have Game's player() be optional, considering it should always be present.
* => this means People need to exist before Game.
* Perhaps Game.create() needs People passed in?
* It is reasonable to assume that people can be created before a game, though they do not make much sense to be so
* Perhaps a missing domain concept?
* What creates the people?