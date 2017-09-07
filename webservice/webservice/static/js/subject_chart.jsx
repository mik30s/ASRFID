import axios from 'axios';
import React from 'react';
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
		}
	}

	update () {
        // make get request to 
        // /get_latest
        console.log("updating...");
        axios.get(`http://localhost:8000/get_latest`)
             .then(res => {
                this.state.data = res.data.slice();
                // prepare data for ploting
                this.state.data.map((obj) => {

                });

                // update chart
                this.state.chart.data = this.state.data.slice();
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
		// reset the charts height
		this.state.height = this.divElement.clientHeight;
		//console.log("chart height: "+ this.state.height);
		const ctx = document.getElementById('subject-chart-canvas')
							.getContext('2d');
		ctx.canvas.width = this.state.width;
		ctx.canvas.height = this.state.height;
		this.state.chart = new Chart(ctx, {
			type: 'scatter',
			data: [],
            options: {
                scales: {
                    xAxes: [{
                        type: 'linear',
                        position: 'bottom'
                    }]
                }
            }
		});
        update();
        window.setInterval(() => this.update(), 3000);
	}

}

module.exports = SubjectChart;