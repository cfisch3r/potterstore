# Potter Store
This tutorial for outside-in Test Driven Development (aka London School) is based on the famous [Potter Kata](http://codingdojo.org/kata/Potter/).
The goal of the excercise is to build a REST Service which provides a price calculation for a list of books. 

# Business Rules

* There are 5 different titles on sale at the moment.
* Discounts are:
	* 5% for 2 book series
	* 10% for 3 book series
	* 20% for 4 book series
	* 25% for 5 book series

# Branches

Branch   | Description
---------|-------------
part1    | start of first User Story
part1end | solution of first User Story
part2    | start of second User Story
part2end | solution of second User Story 


# Technical Guides

## Configuration Properties
Details about Type-Save Configuration Properties can be found in the [Spring Guide](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html).

## REST Controller
See this [Guide](https://spring.io/guides/gs/rest-service/) for an introduction to Spring REST COntroller and Request Mapping.

## Spring Web Layer Testing
For tests with Spring MockMVC see this [Guide](https://spring.io/guides/gs/testing-web/).

## JSON Path
The documentation for jsonpath can be found [here](https://github.com/json-path/JsonPath).

## CRUD Repository
How to access data from the database using Spring Data's CrudRepository is explained [here](https://docs.spring.io/spring-data/data-commons/docs/1.6.1.RELEASE/reference/html/repositories.html). 
