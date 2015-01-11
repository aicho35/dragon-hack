var io = require('socket.io').listen(3000),
	autonomy = require('ardrone-autonomy');;

var mission = autonomy.createMission(),
	control = mission.control(),
	client = mission.client();

io.on('connection', function (socket) {

	console.log('connect');

	socket.on('', function(){
		console.log()
	});

	socket.on('takeoff', function () {
		console.log('takeoff')
		//client.disableEmergency()
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
			control.right(0.6);
			//s.x -= 0.1;

			console.log('Right');
		}
		else{
			//s.x += 0.1;
			control.left(0.6);
			console.log('Left')
		}



		if(data.throt > 0){
			control.backward(0.6);
		}
		else if(data.throt < 0){
			control.forward(0.6);
		}



		//control.go(s);
	});

	socket.on('land', function(){
		mission.client().stop();
		mission.client().land();
	});

//	socket.emit('takeoff');

});

/*
setTimeout(function(){
	control.zero();
		client.takeoff(function(){
			control.altitude(0.5);
		});

})
*/


process.on('SIGINT', function(){
	mission.client().stop();
	mission.client().land(function(){
	});


	setTimeout(function(){
		process.exit();
	}, 6000);

});


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
