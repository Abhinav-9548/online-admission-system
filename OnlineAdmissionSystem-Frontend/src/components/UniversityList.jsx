import { useNavigate, useParams } from "react-router-dom";
import { getUniversityDetailsByCity } from "../Services/UniversityService";
import { useEffect, useState } from "react";
import Button from "react-bootstrap/Button";
// import ListGroup from 'react-bootstrap/ListGroup';
// import Card from 'react-bootstrap/Card';
import { Card, ListGroup } from "react-bootstrap";

const UniversityList = () => {
  const [university, setUniversity] = useState([]);

  const { city } = useParams();
  const navigator = useNavigate();
  useEffect(() => {
    getUniversityDetailsByCity(city)
      .then((response) => {
        setUniversity(response.data);
        console.log(university);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  function handleColleges(Id) {
    navigator(`/colleges/${Id}`);
  }

  function data() {
    if (university.length > 0) {
      return (
        <>
          {/* <br />
          <br />
          <br />
          <br /> */}
          <h1 className="align-centre">Universities in {city}</h1>
          <br />
          <div className="card-container">
            {university.map((university) => (
              <Card key={university.universityId} className="custom-card">
                <Card.Img variant="top" src={"/src/assets/image4.jpg"} />
                <Card.Body>
                  <Card.Title>{university.name}</Card.Title>
                  <Card.Text className="b"><i>Address</i></Card.Text>
                </Card.Body>
                <ListGroup className="list-group-flush">
                  {/* <ListGroup.Item>
                    {university.address.city}, {university.address.state},
                    {university.address.country}, {university.address.zipcode}{" "}
                  </ListGroup.Item> */}
                  <ListGroup.Item style={{backgroundColor:'#4dcecc', borderRadius:'10px'}}>
                    City: {university.address.city}
                  </ListGroup.Item>
                  <ListGroup.Item style={{backgroundColor:'#4dcecc', borderRadius:'10px'}}>
                    State: {university.address.state}
                  </ListGroup.Item>
                  <ListGroup.Item style={{backgroundColor:'#4dcecc', borderRadius:'10px'}}>
                    Country: {university.address.country}
                  </ListGroup.Item>
                  <ListGroup.Item style={{backgroundColor:'#4dcecc', borderRadius:'10px'}}>
                    Zip-Code: {university.address.zipcode}
                  </ListGroup.Item>
                </ListGroup>
                <Card.Body>
                  <Card.Link
                    href=""
                    onClick={(e) => {
                      e.preventDefault();
                      handleColleges(university.universityId); // Call your function
                    }}
                    className="custom-link"
                  >
                    Colleges under {university.name}
                  </Card.Link>
                  {/* <Card.Link href="#">Another Link</Card.Link> */}
                </Card.Body>
              </Card>
            ))}
          </div>
        </>
      );
    } else {
      return (
        <div className="no">
          <h2 className="noData">No University Found in {city}</h2>
        </div>
      );
    }
  }

  return (
    <div className="universitydiv">
      {data()}
    </div>
  );
};

export default UniversityList;
