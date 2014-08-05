ssss
====

TODO Priority list
* Reduce to one domain module until such time as more complexity is desired
* Ensure Presenters subscribe to events
* Convert UI to services
* State model for UI?
  * Consider this for splitting view creation from view behaviour
* Important worry... UnitOfWork open during Player update?
  * This is due to domain calling into ui layer with uow still open
    * Does this mean the SideEffect fires before the concern wraps up?
  * When does the stack end?

Next
* Tidy up player stuff
* Work out reads vs commands better
* proper cqrs with events?

Notes
* Domain objects have no setters or getters. Just their domain concepts.
* Domain objects should not leak outside of their layer. Use Dtos.

Thoughtsi
* ill inclined to have Game's player() be optional, considering it should always be present.
* => this means People need to exist before Game.
* Perhaps Game.create() needs People passed in?
* It is reasonable to assume that people can be created before a game, though they do not make much sense to be so
* Perhaps a missing domain concept?
* What creates the people?

Visibility?
* Want to know when Service A in module B gets an entity by calling Service F in module G, but UoW has completed?
* How to do cross-module associations? Is this ill advised?

Associations
* Are these to build an AR, or for relationships between ARs?
  * i.e. Do deletes cascade through associations?
* If ARs are in different modules, then can you look them up?
  * i.e. should you have layer visibility for entities?
* Can/should the same entity exist in multiple modules (at module visibility).
  * If so, is a get by id in two modules going to find the same entity?

Thoughts
Inter-module dependencies would be inverted if firing events?
e.g. instead of Game calling People, Game fires event, People subscribes.

Queries
* Queries can be returned from a repository, paused, to provide custom paging etc
* When needing to retrieve on business logic, use an Entity get() followed by a traversal
* Finally, consider long-term how third party plugins may wish to do custom queries in existing modules