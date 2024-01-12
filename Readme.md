# The following API tests-cases execute 4 HTTP methods of user services:

    - POST - Adding a new user to the service.
The following bugs were found to this method:
- ID could be added manually **as a String**, instead of an incremental Integer.
- An empty object could be added, without name or ID.
- An object without name could be added.
- An object without ID could be added, therefore, unable to be fetched by it.   
In addition, this causes a **critical bug** regarding getting an object by ID.
- Duplicate IDs could be assigned upon creation, which retrieves only the last object.


    - GET - Fetches an object by a query.
    As for now, it is either by object's ID or the entire list.

    - PUT - Edits an object by target ID & objects' properties.
The following bugs were found to this method:
- The response indicates that the object was updated, but when retrieving it again, **the changes are not set**.


    - DELETE - Deletes an object by a query.
The following bugs were found to this method:
- Upon delete attempt, '500' server status code is retrieved, therefore, the object **could not** be deleted.