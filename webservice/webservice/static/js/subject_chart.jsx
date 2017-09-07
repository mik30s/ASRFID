import axios from 'axios';
import React from 'react';
import moment from 'moment';
import Chart from 'chart.js';

import '../css/subject_chart.scss';

class SubjectChart extends React.Component {
	constructor(props) {
		super();
		this.state = {
            data:[],
            chart:{},
            width: 0,
			height: 0,
            maxTime: 180,
            // epoch: '03:15:33'
		}
	}

	update () {
        // make get request to 
        // /get_latest
        console.log("updating...");
        axios.get(`http://localhost:8000/get_latest`)
             .then(res => {
             	const hrs = 0, mins = 1, secs = 2
                this.state.data = res.data.slice();
                let convertedMins = 0
                let finalData = []
                // prepare data for ploting
                this.state.data.map((obj) => {
                	let t = obj.start_time.split('T')[1].slice(0,-1);
                	console.log(t)
                	let a = t.split(':').filter(w => {return w !== ""});
                	console.log(a);
                	if (a[hrs] === this.state.epoch)
                	 	convertedMins = 0;
                	else {
                		// convert hourst to minutes
                		convertedMins = (parseInt(a[hrs]) * 60) + parseInt(a[mins]);
                	}
                	finalData.push({"x" :convertedMins, "y" : obj.duration});
                });

                // update chart
                this.state.chart.data.datasets[0].data = finalData.slice();
                console.log(this.state.chart.data.datasets[0].data)
                this.state.chart.update();
            });
	}

	render () {
		return (
			// get a reference to the div first
			<div 
				id = "subject-chart-root"
				ref = { (divElement) => this.divElement = divElement}
			>
				<canvas id="subject-chart-canvas"></canvas>
			</div>
		)
	}

	componentDidMount () {
		Chart.defaults.global.elements.point.radius = 6;
		Chart.defaults.global.animationSteps = 50;
		Chart.defaults.global.tooltipYPadding = 16;
		Chart.defaults.global.tooltipCornerRadius = 0;
		Chart.defaults.global.tooltipTitleFontStyle = "normal";
		Chart.defaults.global.tooltipFillColor = "rgba(0,160,0,0.8)";
		Chart.defaults.global.animationEasing = "easeOutBounce";
		Chart.defaults.global.responsive = true;
		Chart.defaults.global.scaleLineColor = "black";
		Chart.defaults.global.scaleFontSize = 16;
		// reset the charts height
	    var config = {
            type: 'scatter',
            pointDotRadius: 10,
            data: {
                // labels: ["January", "February", "March", "April", "May", "June", "July"],
                datasets: [{
            		showLine: false,
                	borderColor: 'rgba(0,1,0,1)',
                	backgroundColor: 'rgba(0,225,0,1)',
                    fillColor: "rgba(0,220,0,1)",
                    // label: "My First dataset",
                    data: [
                        /*{
                        x:180,
                        y:90,
                    },
                    {  
                    	x: 0,
                        y: 70,
                    }*/],
                    fill: true,
                }]
            },
            options: {
                responsive: true,
                title:{
                    display:true,
                    text:'Subject duration trends'
                },
                tooltips: {
                    mode: 'index',
                    intersect: false,
                },
                hover: {
                    mode: 'nearest',
                    intersect: true
                },
                scales: {
                    xAxes: [{
                        display: true,
                        scaleLabel: {
                            display: true,
                            labelString: 'End Time (minutes from daybreak)'
                        },
                        gridLines: {
						  display: true ,
						  color: "rgba(80,80,80)"
						},
                    }],
                    yAxes: [{
                        display: true,
                        scaleLabel: {
                            display: true,
                            labelString: 'Duration (seconds)'
                        },
                    	gridLines: {
						  display: true ,
						  color: "rgba(80,80,80)"
						},                    
					}],
                }
            }
        };

		this.state.height = this.divElement.clientHeight;
		const ctx = document.getElementById('subject-chart-canvas')
							.getContext('2d');
		ctx.canvas.style.width = this.state.width;
		ctx.canvas.parentNode.style.height = this.state.height - 100;
		
        this.state.chart = new Chart(ctx, config);
        this.update();
        
        window.setInterval(() => this.update(), 3000);
	}
}

module.exports = SubjectChart;