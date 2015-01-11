/*
var arDrone = require('ar-drone');
var client = arDrone.createClient();

client.takeoff();


client.after(5000, function(){
	this.stop();
	this.land();
});
*/


var autonomy = require('ardrone-autonomy');
var mission  = autonomy.createMission();


mission.takeoff()
       .zero()       // Sets the current state as the reference
       .altitude(0.5)  // Climb to altitude = 1 meter
//       .forward(2)
       .right(0.4)
		.wait(1000)
//       .backward(2)
//       .left(2)
       .hover(5000)  // Hover in place for 1 second
       .land();

//mission.client().disableEmergency();


process.on('SIGINT', function(){
	mission.client().stop();
	mission.client().land(function(){
	});


	setTimeout(function(){
		process.exit();
	}, 6000);

});

mission.run(function (err, result) {
    if (err) {
        console.trace("Oops, something bad happened: %s", err.message);
        mission.client().stop();
        mission.client().land();
    } else {
        console.log("Mission success!");
        process.exit(0);
    }
});



