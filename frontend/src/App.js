import React from 'react';
import { Routes, Route} from 'react-router-dom';

import Category from './pages/Category';
import NewsList from './pages/NewsList';
import NavBar from './components/NavBar';
import News from './pages/News';

function App() {

  return (
    <div style={{ 
      maxWidth: '480px', 
      margin: '0 auto', 
      position: 'relative', 
      minHeight: '100vh',
      backgroundColor: '#F5F4F5',
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center',
      paddingBottom: '80px'
    }}>
      <Routes>
        <Route path="/" element={<Category />} />
        <Route path="/category/*" element={<NewsList />} />
        <Route path="/new" element={<News />} />
      </Routes>
      <div style={{ 
        position: 'fixed', 
        bottom: 0, 
        width: '100%', 
        maxWidth: '480px', 
        margin: '0 auto', 
        left: '50%', 
        transform: 'translateX(-50%)',
        zIndex: 1000
      }}>
        <NavBar />
      </div>
    </div>
  );
}

export default App;
