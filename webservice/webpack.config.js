var debug = process.env.NODE_ENV !== "production";
var webpack= require('webpack');

module.exports = {
	context: __dirname,
	devtool: debug ? "inline-sourcemap" : null,
	entry: __dirname + "/public/main.js",
	output: {
		path: __dirname + "/public/build",
		filename: "build.min.js"
	},
	module: {
	  rules: [
	    {
	      test: /\.js$/,
	      exclude: /(node_modules|bower_components)/,
	      use: {
	        loader: 'babel-loader',
	        options: {
	          presets: ['env']
	        }
	      }
	    }
	  ]
	}
};