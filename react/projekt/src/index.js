import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';

import { SessionProvider } from './components/SessionContext';


const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <SessionProvider>
  <React.StrictMode>
    <App />
  </React.StrictMode>
  </SessionProvider>
);
