M101J Assignment 4.3
====================
This is the assignment for Homework 4.3 of the [M101J](https://education.10gen.com/) MongoDB online course translated to [Scala](http://www.scala-lang.org/) and [Play Framework](http://www.playframework.com/).

This project uses the official MongoDB Scala driver [Casbah](http://api.mongodb.org/scala/casbah/current/index.html).

The integration of Casbah into Play is done by the [MongoDB Salat Plugin](https://github.com/leon/play-salat) for the Play Framework.
We don't use the ORM features of this plugin, only the connection management.

The Assignment
--------------
You actually don't need to implement anything this week. The assignment is to optimize the blog's performance.
You don't even need the blog running to pass the validation. Just follow the instructions from the official assignment page.

I only updated the blog application to bring the Scala/Play version up to par with the original Java/Spark version 

Side note
---------
This project uses the [Cake Pattern](http://jonasboner.com/2008/10/06/real-world-scala-dependency-injection-di/) to glue together the application - specifically the `BlogController` to the DAOs.
You don't actually need to understand the whole application, although I encourage you to dig into it.

And if you look at the code, you will notice that this project does not properly separate the MVC layers.
Passing MongoDB-specific objects created by the database driver (model) to templates (view) is not something
you want to do in a real-world application. Fixing this design problem however, would require additional work
and would introduce more differences from the original Java/Spark version. 


Disclaimer
----------
This project is provided "as is". It has not been properly tested and there is no official support for it
provided by anyone (especially not by 10gen). It is intended for M101J's students interested in Scala and
it will most likely be harder to pass the course using this instead of the official Java + Spark version.

Last but not least: Do not publicly disclose solutions to the course's homeworks.
