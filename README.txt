Software Design Final Project Phase 1
Authors: Tao Liu and Xi Chen
Core classes:
* Record: This class contains a list of transactions. It is used to serialize and deserialize a list of transactions. 
* Transaction: This class contains the information like type, date, value, category. And it’s the base unit of our application because all our views are organized around the collections of transaction objects.
* Piechart: This class provides one view of collections of transaction objects, in pie chart. The view consists of two pie charts, one for expense analytics and the other one for income analytics.
   * Daily Piechart: This class inherits from Piechart class, and only renders the collections of transactions occurred today.
   * Weekly Piechart: This class inherits from Piechart class, and only renders the collections of transactions occurred in this week.
   * Monthly Piechart: This class inherits from Piechart class, and only renders the collections of transactions occurred in this month.
   * Annual Piechart: This class inherits from Piechart class, and only renders the collections of transactions occurred in this year.
* Listview: This class provides another view of collections of transaction objects, in table-like list view.
   * Daily Listview: This class inherits from Listview class, and only renders the collections of transactions occurred today.
   * Weekly Listview: This class inherits from Listview class, and only renders the collections of transactions occurred in this week.
   * Monthly Listview: This class inherits from Listview class, and only renders the collections of transactions occurred in this month.
   * Annual Listview: This class inherits from Listview class, and only renders the collections of transactions occurred in this year.
* Line Chart: This class provides user with a third view of collections of transaction objects, in line chart.
   * Daily Linechart: This class inherits from Linechart class, and only renders the collections of transactions occurred today.
   * Weekly Linechart: This class inherits from Linechart class, and only renders the collections of transactions occurred in this week.
   * Monthly Linechart: This class inherits from Linechart class, and only renders the collections of transactions occurred in this month.
   * Annual Linechart: This class inherits from Linechart class, and only renders the collections of transactions occurred in this year.


Note: Currently, our app could build and run, but it’s still buggy and may crash if playing around. But the core architecture has been set up.