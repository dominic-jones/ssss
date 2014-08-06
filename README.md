ssss
====

TODO Priority list
* Simplify / standardize event creation
* Ensure Presenters subscribe to events
* State model for UI? Consider removing state from services?
  * Consider this for splitting view creation from view behaviour
* Important worry... UnitOfWork open during Player update?
  * This is due to domain calling into ui layer with uow still open
    * Does this mean the SideEffect fires before the concern wraps up?
  * When does the stack end?
* Events need to become Qi4j Values
* Consider bootstrap through an AppStart event

Thoughts
* Ill inclined to have Game's player() be optional, considering it should always be present.
  * This means People need to exist before Game.?
* Perhaps Game.create() needs People passed in?
* It is reasonable to assume that people can be created before a game, though they do not make much sense to be so
* Perhaps a missing domain concept?
* What creates the people?

Visibility?
* Want to know when Service A in module B gets an entity by calling Service F in module G, but UoW has completed?
* How to do cross-module associations? Is this ill advised?

Questions re: Associations
* Are these to build an AR, or for relationships between ARs?
  * i.e. Do deletes cascade through associations?
* If ARs are in different modules, then can you look them up?
  * i.e. should you have layer visibility for entities?
* Can/should the same entity exist in multiple modules (at module visibility).
  * If so, is a get by id in two modules going to find the same entity?
* Can one apply sorting to an association? (Beyond standard Java)

Queries
* Queries can be returned from a repository, paused, to provide custom paging etc
* When needing to retrieve on business logic, use an Entity get() followed by a traversal
* Finally, consider long-term how third party plugins may wish to do custom queries in existing modules

Findings
* Inter-module dependencies are inverted when firing events
  * e.g. If Game would depend on Player to RPC, instead Player depends on Game to observe Game's events
* Domain objects have no setters or getters. Just their domain concepts.
* Domain objects should not leak outside of their layer. Use Dtos.