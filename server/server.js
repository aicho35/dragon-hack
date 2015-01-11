var io = require('socket.io').listen(3000),
	autonomy = require('ardrone-autonomy');;

var mission = autonomy.createMission(),
	control = mission.control(),
	client = mission.client();

io.on('connection', function (socket) {

	socket.on('takeoff', function () {
		client.disableEmergency()
		control.zero();
		client.takeoff(function(){
			control.altitude(0.5);
		});

		//console.log(data);
	});

	socket.on('update', function(data){
		console.log(data);

		var cur = control.state();

		var s = {x: cur.x, y: cur.y, z: cur.z, yaw: cur.yaw};

		if(data.dx > 0){ // Go camera right
			control.left(0.2);
			//s.x -= 0.1;

			console.log('Right');
		}
		else{
			//s.x += 0.1;
			control.right(0.2);
			console.log('Left')
		}



		/*

		if(data.throt > 0){
			control.backward(0.1);
		}
		else if(data.throt < 0){
			control.forward(0.1);
		}



		*/



		//control.go(s);
	});

	socket.on('land', function(){


	});

//	socket.emit('takeoff');

});

setTimeout(function(){
	control.zero();
		client.takeoff(function(){
			control.altitude(0.5);
		});

})


process.on('SIGINT', function(){
	mission.client().stop();
	mission.client().land(function(){
	});


	setTimeout(function(){
		process.exit();
	}, 6000);

})


/*
mission.takeoff()
       .zero()       // Sets the current state as the reference
       .altitude(1)  // Climb to altitude = 1 meter
       .forward(2)
       .right(2)
       .backward(2)
       .left(2)
       .hover(1000)  // Hover in place for 1 second
       .land();

mission.run(function (err, result)
    if (err) {
        console.trace("Oops, something bad happened: %s", err.message);
        mission.client().stop();
        mission.client().land();
    } else {
        console.log("Mission success!");
        process.exit(0);
    }
});
*/
