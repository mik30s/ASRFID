import React from 'react';
import ProgressBar from 'progressbar.js';
import '../css/summary_panel.scss';

class SummaryPanel extends React.Component {
	constructor(props) {
		super();
	}

	onUpdate () {

	}

	render () {
		return (
			<div className="summary-panel-root">
				<div id="average-time" ></div>
			</div>
		)
	}

	componentDidMount() {
		const averageTimeBar = new ProgressBar.Circle("#average-time", {
			easing: 'easeInOut',
			color: '#222',
  			// This has to be the same size as the maximum width to
  			// prevent clipping
  			strokeWidth: 4,
  			trailWidth: 1,

  			step: (state, circle) => {

  			}
		});


	}
}

module.exports = SummaryPanel;