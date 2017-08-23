var express = require('express')
var mysql = require('mysql');
var ejs = require('ejs');
var app = express();
var auth = require(__dirname + '/auth.json');
var dbConnection = null;

app.use(express.static(__dirname + 'public/'));
// initalize data base connection
app.use(function(req, resp, next){
	dbConnection = mysql.createConnection({
		host: "localhost",
		user: auth.dbUser,
		password: auth.dbPass,
	});

	dbConnection.connect(function(err){
		if (err) throw err;
		console.log("connected!");
	});

	next();
});

// set the view engine to ejs
app.set('view engine', 'ejs');


// get response for home
app.get('/', function(req, resp, next) {
	resp.render('index', {title: 'ASRFID stats'});
});

// get all patient data for client side processing
app.get('/patients_data', function(req, resp, next){

});

// start server
app.listen(process.env.PORT || 3000, function(){
	console.log('Listening on ' + (process.env.PORT || 3000));
});