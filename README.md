ssss
====
Next
* Tidy up player stuff
* Work out reads vs commands better
* proper cqrs with events?
* Hexagonal

Notes
* Domain objects have no setters or getters. Just their domain concepts.
* Domain objects should not leak outside of their layer. Use Dtos.

Thoughts
* ill inclined to have Game's player() be optional, considering it should always be present.
* => this means People need to exist before Game.
* Perhaps Game.create() needs People passed in?
* It is reasonable to assume that people can be created before a game, though they do not make much sense to be so
* Perhaps a missing domain concept?
* What creates the people?