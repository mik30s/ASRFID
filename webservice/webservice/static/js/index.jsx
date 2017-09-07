// js components
import React from 'react';
import {render} from 'react-dom';
import SubjectChart from './subject_chart.jsx';
import SummaryPanel from './summary_panel.jsx';

// style
import '../css/index.scss';

class App extends React.Component {
	render() {
		return (
			<div className="app-root">
				<SubjectChart />
				<SummaryPanel />
			</div>
		);
	}
}

render(<App />, document.getElementById('app-mount'));