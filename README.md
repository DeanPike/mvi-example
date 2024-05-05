This is an example project that uses the mvi architecture for UI interactions.
Network calls are made using Retrofit.
Dependency injection is done using Hilt

The modules used are:
app - The main module that ties everything together.
client - This in the interface between the UI and "backend" modules.
data - This module has all of the use cases, repositories and data sources needed to retrieve information for the network.
data-shared - This contains common code that can be shared amongst many data modules.
network - This contain all of the Retrofit API's anf their data classes
ui-test-shared - Utility classes for UI tests
unit-test-shared =Utility classes for unit tests
ui - Contains the screen that the user will interact with
ui-shared - Contains Themes and any other classes that can be used by multiple UI modules 