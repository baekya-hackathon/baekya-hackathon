import React from 'react';
import { Navbar, Nav, Container } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import { FaHome, FaSearch } from 'react-icons/fa';

function NavBar() {
    return (
        <Navbar bg="light" fixed="bottom" style={{
            borderTopLeftRadius: '20px',
            borderTopRightRadius: '20px',
            boxShadow: '0px -2px 10px rgba(126, 55, 249, 0.2)',
            padding: '15px 0',
            margin: '0 10px'
        }}>
            <Container>
                <Nav className="w-100 d-flex justify-content-around px-4">
                    <Nav.Link href="/" className="text-center" style={{
                        color: '#7E37F9',
                        transition: 'all 0.2s ease-in-out'
                    }}>
                        <FaHome size={35} style={{ 
                            marginBottom: '5px',
                            transition: 'all 0.2s ease-in-out'
                        }} />
                        <div className="mt-2" style={{ 
                            fontSize: '20px',
                            fontWeight: '700',
                            transition: 'all 0.2s ease-in-out'
                        }}>홈</div>
                    </Nav.Link>
                    
                </Nav>
            </Container>
        </Navbar>
    );
}

export default NavBar;
