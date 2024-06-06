import React from 'react'
import Navbar from 'react-bootstrap/Navbar';
import Nav from 'react-bootstrap/Nav';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faHome } from '@fortawesome/free-solid-svg-icons';
import NavDropdown from 'react-bootstrap/NavDropdown';


const NewComponent = () => {
  return (
    <div>
      <Navbar bg="dark" variant="dark" expand="lg">
        <Navbar.Brand href="#">
          <FontAwesomeIcon icon={faHome} /> University Name
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="ml-auto">
            <Nav.Link href="#">Programs</Nav.Link>
            <Nav.Link href="#">Admissions</Nav.Link>
            <Nav.Link href="#">About</Nav.Link>
            <Nav.Link href="#">Contact</Nav.Link>
          </Nav>
        </Navbar.Collapse>
      </Navbar>
    </div>


//     <div>
//       <Navbar bg="dark" variant="dark" expand="lg">
//         <Navbar.Brand href="#">University Name</Navbar.Brand>
//         <Navbar.Toggle aria-controls="basic-navbar-nav" />
//         <Navbar.Collapse id="basic-navbar-nav">
//           <Nav className="mr-auto">
//             <Nav.Link href="#">Home</Nav.Link>
//             <Nav.Link href="#">Programs</Nav.Link>
//             <NavDropdown title="Admissions" id="basic-nav-dropdown">
//               <NavDropdown.Item href="#">Undergraduate</NavDropdown.Item>
//               <NavDropdown.Item href="#">Postgraduate</NavDropdown.Item>
//               <NavDropdown.Divider />
//               <NavDropdown.Item href="#">Apply Now</NavDropdown.Item>
//             </NavDropdown>
//             <Nav.Link href="#">About</Nav.Link>
//             <Nav.Link href="#">Contact</Nav.Link>
//           </Nav>
//         </Navbar.Collapse>
//         <form className="form-inline my-2 my-lg-0">
//             <input className="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search" />
//             <button className="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
//             </form>
//       </Navbar>
// </div>





  
  )
}

export default NewComponent