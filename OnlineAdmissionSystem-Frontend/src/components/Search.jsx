import React, { useEffect, useState } from "react";
// import { useNavigate } from "react-router-dom";
import { Container, Card ,Button} from 'react-bootstrap';
import {useNavigate, useParams } from "react-router-dom";
import { getCollegeByAll } from "../Services/CollegeService";


function Search() {



    const [college, setCollege] = useState([]);
  const [university, setUniversity] = useState('');
 
  const navigator = useNavigate();
  const {searchTerm} = useParams();
   
   
  useEffect(() => {
    getAllColleges();
  }, [searchTerm]);
 
  function getAllColleges(){
   
      getCollegeByAll(searchTerm).then((response) => {
        console.log("functon")
        if(response.data.length>0)
        {
            console.log("if block")
            setCollege(response.data);
 
        }else{
            // setCollege('')
            navigator(`/college-detail/${searchTerm}`)
            console.log("not found")
            console.log("catch")
           
        }
       
       
      })
      .catch((error) => {
        setCollege('')
        navigator(`/college-detail/${searchTerm}`)
        console.log("catch")
        console.error(error);
      });
  }
 
 
  function handleProgramOffer(Id){
    navigator(`/program/${Id}`)
  }

    function collegeIsEmpty()
    {
        if(college.length>0)
        {
            return(
                <div>
        <Container>
          {/* <h2 className="text-center mb-4 "></h2> */}
          <div className="row">
            {college.map((college) => (
              <div key={college.collegeRegId} className="col-md-6 mb-4">
                <Card className="collegeCard">
                  <Card.Body>
                    <Card.Title><b><u>{college.collegeName}</u></b></Card.Title>
                    <Card.Text><strong>Location:</strong> {college.address.addressId}, {college.address.city}, {college.address.state}, {college.address.country}<br/>{college.address.zipcode}</Card.Text>
                    <Card.Text><strong>Description:</strong> {college.description}</Card.Text>
                    <Button type="button" className="btn btn-info mb-2" onClick={()=>handleProgramOffer(college.collegeRegId)}>
                        Programs offered
                      </Button>
                  </Card.Body>
                </Card>
              </div>
            ))}
          </div>
        </Container>
        </div>
            )
        }else{
            return(
             
                
                     
                     <div className="no">
                <h2 className="noData">No College Found</h2>
              </div>
     
                
            )
        }
    }


  return (
    <div  className='searchdiv'>
    {collegeIsEmpty()}
    </div>
  )
}

export default Search