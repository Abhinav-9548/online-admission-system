import React, { useEffect, useRef, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHome } from "@fortawesome/free-solid-svg-icons";
import NavDropdown from "react-bootstrap/NavDropdown";
import { InputGroup, FormControl, Button } from "react-bootstrap";
// import { FaSearch } from "react-icons/fa";
import "bootstrap/dist/css/bootstrap.min.css";
import { getAllUniversity } from "../Services/UniversityService";
import { NavLink } from "react-router-dom";
import {
  faUserCircle,
  faSearch
} from "@fortawesome/free-solid-svg-icons";

function Header() {
// Search bar-------------------------------------------------------------------------
  const [searchOpen, setSearchOpen] = useState(false);
  const searchRef = useRef(null);
  const [searchTerm, setSearchTerm] = useState("");
  const [errorMessage, setErrorMessage] = useState('');

  const toggleSearch = () => {
    setSearchOpen(!searchOpen);
  };


  const handleClickOutside = (event) => {
    if (searchRef.current && !searchRef.current.contains(event.target)) {
        setSearchOpen(false);
    }
};

useEffect(() => {
    document.addEventListener('mousedown', handleClickOutside);
    return () => {
        document.removeEventListener('mousedown', handleClickOutside);
    };
}, []);

// -------------------------------------------------------------------------------------

  const navigator = useNavigate();
  const [university, setUniversity] = useState([]);

  useEffect(() => {
    getAllUniversity()
      .then((response) => {
        setUniversity(response.data);
        console.log(university);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  function handleGetUniversity(universityId) {
    navigator(`/Colleges/${universityId}`);
  }
  function handleAbout() {
    navigator(`/about`);
  }

  function handleContact() {
    navigator(`/contact`);
  }
  function handleAdminDarshboard()
  {
    navigator(`/admindashboard`);
  }

  function handleProgram() {
    navigator(`/admin`);
  }
  // function handleAdminList() {
  //   navigator(`/university-list`);
  // }

  function handleDarshboard()
  {
    navigator(`/dashboard`);
  }

  function handleSearch(e) {
    e.preventDefault();
    if (!searchTerm.trim()) {
      setErrorMessage('Search term cannot be empty');
    } else {
      setErrorMessage('');

    navigator(`/college-detail/${searchTerm}`);
    }
  }
  return (
    <div className="header">
      <Navbar className="navbar" variant="white" expand="lg">
        <NavLink to="/" className="nav-link">
          <Navbar.Brand style={{ marginLeft: "20px" }}>
            <FontAwesomeIcon icon={faHome} />
          </Navbar.Brand>
        </NavLink>

        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="mr-auto">
            {/* <Nav.Link href="#">Online Admission System</Nav.Link> */}
            {/* <Nav.Link onClick={handleProgram}>Programs</Nav.Link> */}
            {/* <NavDropdown title="Admissions" id="basic-nav-dropdown">
              <NavDropdown.Item >
                Undergraduate
              </NavDropdown.Item>
              <NavDropdown.Item href="#">Postgraduate</NavDropdown.Item>
              <NavDropdown.Divider />
              <NavDropdown.Item href="#">Apply Now</NavDropdown.Item>
            </NavDropdown> */}
            <NavDropdown title="University" id="basic-nav-dropdown">
              {university.map((uni) => (
                <NavDropdown.Item
                  key={uni.universityId}
                  onClick={() => handleGetUniversity(uni.universityId)}
                >
                  {uni.name}
                </NavDropdown.Item>
              ))}
            </NavDropdown>
            <Nav.Link onClick={handleAbout}>About</Nav.Link>
            <Nav.Link onClick={handleContact}>Contact</Nav.Link>
          </Nav>
        </Navbar.Collapse>

      

        <div ref={searchRef} className="search-container">





          
            {!searchOpen && (
              <FontAwesomeIcon
                icon={faSearch}
                className="search-icon"
                onClick={toggleSearch}
              />
            )}
            {searchOpen && (
              <form className="form-inline mr-4">
                <input
                  className="form-control mr-sm-2"
                  type="search"
                  placeholder="Search"
                  aria-label="Search"
                  value={searchTerm}
                 
                  onChange={(e) => {
                    setSearchTerm(e.target.value);
                  }}
                />
                <button
                  className="btn btn-outline-success my-2 my-sm-0"
                  type="submit"
                  onClick={handleSearch}
                  
                >
                  Search
                </button>
                {/* {errorMessage && <div className="error-message" style={{ color: 'red' }}>{errorMessage}</div>} */}
              </form>
            )}



            {/* Signed in as............................................................ */}
        <Navbar.Collapse className="justify-content-end mr-4">
          <Navbar.Text>
            Signed in as: <a href="#login">Chirag Nimesh</a>
          </Navbar.Text>
        </Navbar.Collapse>
        </div>
        

        <ul className="navbar-nav profile">
          <li className="nav-item dropdown">
            <a
              className="nav-link dropdown-toggle"
              href="#"
              id="profileDropdown"
              role="button"
              data-toggle="dropdown"
              aria-haspopup="true"
              aria-expanded="false"
            >
              <FontAwesomeIcon icon={faUserCircle} size="lg" />
            </a>
            <div
              className="dropdown-menu dropdown-menu-right"
              aria-labelledby="profileDropdown"
            >
              <a className="dropdown-item" onClick={handleDarshboard}>
                Student Dashboard
              </a>
              <a className="dropdown-item" onClick={handleAdminDarshboard}>
                Admin Dashboard
              </a>
              
              <div className="dropdown-divider"></div>
              <a className="dropdown-item" href="#">
                Logout
              </a>
            </div>
          </li>
        </ul>

        {/* <form className="form-inline my-2 my-lg-0 mr-4">
          <input
            className="form-control mr-sm-2"
            type="search"
            placeholder="Search"
            aria-label="Search"
          />
          <button
            className="btn btn-outline-success my-2 my-sm-0"
            type="submit"
          >
            Search
          </button>
        </form> */}
      </Navbar>
    </div>

    // <div>

    //     <nav className="navbar navbar-expand-lg navbar-dark fixed-top" style={{backgroundColor:'black'}}>
    //     <a className="navbar-brand" href="#">Online Admission System</a>
    //     <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    //         <span className="navbar-toggler-icon"></span>
    //     </button>
    //     <Navbar.Brand href="#">
    //       <FontAwesomeIcon icon={faHome} />
    //     </Navbar.Brand>
    //     <div className="collapse navbar-collapse" id="navbarSupportedContent">
    //         <ul className="navbar-nav mr-auto">
    //         {/* <li className="nav-item active">
    //             <a className="nav-link" href="#">Home<span className="sr-only">(current)</span></a>
    //         </li> */}
    //         <li className="nav-item">
    //             <a className="nav-link" href="#">Link</a>
    //         </li>
    //         <li className="nav-item dropdown">
    //             <a className="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
    //             University
    //             </a>
    //             <div className="dropdown-menu" aria-labelledby="navbarDropdown">

    //             <a className="dropdown-item" href="#">AKTU University</a>
    //             <a className="dropdown-item" href="#">Pune University</a>
    //             <a className="dropdown-item" href="#">Delhi University</a>
    //             <a className="dropdown-item" href="#">Punjab University</a>
    //             <a className="dropdown-item" href="#">Mumbai University</a>
    //             <a className="dropdown-item" href="#">Maharashtra University</a>
    //             <a className="dropdown-item" href="#">Chennai University</a>
    //             <a className="dropdown-item" href="#">Banglore University</a>
    //             <a className="dropdown-item" href="#">Bihar University</a>
    //             <div className="dropdown-divider"></div>
    //             <a className="dropdown-item" href="#">Something else here</a>
    //             </div>
    //         </li>
    //         {/* <li className="nav-item">
    //             <a className="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
    //         </li> */}
    //         </ul>
    //         <form className="form-inline my-2 my-lg-0">
    //         <input className="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search" />
    //         <button className="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
    //         </form>
    //     </div>
    //     </nav>
    // </div>
  );
}

export default Header;
