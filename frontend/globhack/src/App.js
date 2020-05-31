import React from 'react';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import Blog from './components/Home/Blog';

const App = () => (
    <Router>
        <Route path = "/" exact  component = {Blog} />
    </Router>
);

export default App;