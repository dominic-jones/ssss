ssss
====

TODO Priority list
* Ensure Presenters subscribe to events
* Use more events for domain results
* More queries

Next
* Tidy up player stuff
* Work out reads vs commands better
* proper cqrs with events?

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

Visibility?
* Want to know when Service A in module B gets an entity by calling Service F in module G, but UoW has completed?
* How to do cross-module associations? Is this ill advised?