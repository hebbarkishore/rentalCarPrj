Instructions:
Requirement:
1.The system should let a customer reserve a car of a given type at a 
  desired date and time for a given number of days
2.The number of cars of each type is limited, but customers should be 
  able to reserve a single rental car for multiple, non-overlapping time frames
3.Provide a Junit test that illustrates the core reservation workflow and 
  demonstrates its correctness.
  

Architecture:
1) This has been developed based on microservices but there is no inbuilt server used, we can host this on any server(preferably on Tomcat since all configuration is ready)
2) I have used Spring framework, used Spring MVC, Context, JPA.
3) Here I have used HSQL in memory DB to save the data temporarily.
4) Used PostConstruct to save the Car information to DB, we have cars_data.json config files which has all the cars available in the application.
5) Here there are 3 services.
   a) fetchAvailableCars - http://localhost:9081/rest-services/service/fetchAvailableCars (GET)
   b) fetchCustomerData - http://localhost:9081/rest-services/service/fetchCustomerData (GET)
   c) carRentalRequest - http://localhost:9081/rest-services/service/carRentalRequest (POST)
      Sample JSON request.
        {
			"customer_nam" : "kishore",
			"booking_date_time" : "2018-11-24T12:00:00",
			"tot_days_booked" : 1,
			"car_make" : "Toyota",
			"car_model" : "Camry"
		}
6) if this service used along with the UI, then Ui can invoke the fetchAvailableCars to check the available cars in the DB.
   same with the fetchCustomerData.
7) carRentalRequest is Post request where from UI, we can frame the JSON request and send the following information,
   customer_nam, booking_date_time, tot_days_booked, car_make and model.
8) Here the Assumption is 
   a) at the given date/time there is only one type  of car make/model available for any Customers.
   Eg : Customer A booked Toyota Camry from 11/19/2018 12pm for 2days then this car will not be available for any one else till 11/21/2018 12pm.
   b) we can assign the same car for multiple date/time for non-overlapping.
   Eg : Customer A booked Toyota Camry from 11/19/2018 12pm for 2days then he can also wish to book the car from 11/21/2018 12pm or any other day which is 
   not overlapping with the existing.
   c) we assume the date format in yyyy-MM-dd'T'HH:mm:ss.
   d) Initially when server starts we load the available cars from the car_data.json file to cars entity.