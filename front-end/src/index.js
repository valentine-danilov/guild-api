import React from 'react';
import ReactDOM from 'react-dom';
import App from './components/App';
import { BrowserRouter } from 'react-router-dom'
import '../src/styles/index.css'
import '../src/styles/bootstrap.css'
import '../src/styles/material-icons.css'
import '../src/styles/fontawesome.css'

ReactDOM.render(
    <BrowserRouter>
        <App />
    </BrowserRouter>,
    document.getElementById('root'));