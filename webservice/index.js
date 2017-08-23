var express = require('express')
var app = express();
var ejs = require('ejs');

app.use(express.static(__dirname + 'public/'));

// set the view engine to ejs
app.set('view engine', 'ejs');


// get response for home
app.get('/', function(req, resp, next) {
	resp.render('index', {title: 'ASRFID stats'});
});


app.listen(process.env.PORT || 3000, function(){
	console.log('Listening on ' + (process.env.PORT || 3000));
});