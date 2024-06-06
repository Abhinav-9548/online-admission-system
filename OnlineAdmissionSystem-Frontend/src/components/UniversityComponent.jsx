import React, { useEffect, useState } from 'react'
import Carousel from 'react-bootstrap/Carousel';
import { useNavigate } from 'react-router-dom';
import { Button, Card } from 'react-bootstrap';

import { Container, Row, Col } from 'react-bootstrap';
import pdf from '../assets/pdff.pdf';


const UniversityComponent = () => {

const navigator = useNavigate();

function handlegetUniversityDetailsByCity(city){
 // e.preventDefault();
navigator(`/university-By-City/${city}`)

}
function handleProgramScheduleApply()
{
  navigator(`/program-schedule-apply`)
}

  return (

<div>
  <div className='coroseldiv'>
 <Carousel fade variant='dark'> 
 
      <Carousel.Item  interval={1000}>
      <img src={'/src/assets/newdelhi.jpg'} onClick={()=>handlegetUniversityDetailsByCity('new delhi')} className="img-fluid coroselcards"  alt="Error In loading image"  />
        {/* <Screenshot  text="First slide" /> */}
        <Carousel.Caption>
          <h3 style={{ color: 'black' }}><b>New Delhi</b></h3>
          <h6 style={{ color: 'black' }}>Lutyens' Delhi</h6>
        </Carousel.Caption>
      </Carousel.Item>
      <Carousel.Item interval={1000}>
        <img src={'/src/assets/hyderabad (2).jpg'} onClick={()=>handlegetUniversityDetailsByCity('hyderabad')} className="img-fluid coroselcards" alt="Error In loading image" />
        {/* <Screenshot  text="First slide" /> */}
        <Carousel.Caption>
          <h3 style={{ color: 'black' }}><b>Hyderabad</b></h3>
          <h6 style={{ color: 'black' }}>The City of Pearls</h6>
        </Carousel.Caption>
      </Carousel.Item>
      <Carousel.Item interval={1000}>
        <img src={'/src/assets/jaipur (2).jpg'}  onClick={()=>handlegetUniversityDetailsByCity('jaipur')} className="img-fluid coroselcards" alt="Error In loading image" />
        {/* <Screenshot  text="First slide" /> */}
        <Carousel.Caption>
          <h3 style={{ color: 'black' }}><b>Jaipur</b></h3>
          <h6 style={{ color: 'black' }}>
          The Pink city
          </h6>
        </Carousel.Caption>
      </Carousel.Item>
      <Carousel.Item interval={1000}> 
        <img src={'/src/assets/chennai (2).jpg'} onClick={()=>handlegetUniversityDetailsByCity('chennai')} className="img-fluid coroselcards" alt="Error In loading image" />
        {/* <Screenshot  text="First slide" /> */}
        <Carousel.Caption>
          <h3 style={{ color: 'black' }}><b>Chennai</b></h3>
          <h6 style={{ color: 'black' }}>
          Madras
          </h6>
        </Carousel.Caption>
      </Carousel.Item>
      <Carousel.Item interval={1000}>
        <img src={'/src/assets/mumbai (2).jpg'} onClick={()=>handlegetUniversityDetailsByCity('mumbai')} className="img-fluid coroselcards" alt="Error In loading image" />
        {/* <Screenshot  text="First slide" /> */}
        <Carousel.Caption>
          <h3 style={{ color: 'black' }}><b>Mumbai</b></h3>
          <h6 style={{ color: 'black' }}>
           Bombay
          </h6>
        </Carousel.Caption>
      </Carousel.Item>
      
    </Carousel> 
    </div>




   
<Container className="d-flex justify-content-center" style={{ alignItems: 'center',flexDirection:"column",marginTop:'60px' }}>

<h5 >Apply here to start your journey! Admissions are now open.</h5>
<Button type="button" style={{width:"300px",marginTop:'10px'}} className="btn btn-info mb-2" onClick={handleProgramScheduleApply}>
                    Apply here
                  </Button>
  </Container>




<div className='upcomingevent'>
  <h3>| Upcoming Events</h3>
<Card className='custom-card-event'>
      <Card.Body>
        <Card.Title>Jul 14, 2024</Card.Title>
        <div style={{display:"flex",flexWrap:"nowrap",justifyContent:"space-between",flexDirection:"row"}}>
        <Card.Text>MATLAB Programming for Physical Chemical Sciences</Card.Text>
        <Card.Link href={pdf} target="_blank" className="custom-link2" >Read more</Card.Link>
        </div>
      </Card.Body>
</Card>
<Card className='custom-card-event'>
      <Card.Body>
        <Card.Title>Jun 30, 2024</Card.Title>
        <div style={{display:"flex",flexWrap:"nowrap",justifyContent:"space-between",flexDirection:"row"}}>
        <Card.Text>Yusuf Hamied Chemistry Camp at DTU</Card.Text>
        <Card.Link href={pdf} target="_blank" className="custom-link2" >Read more</Card.Link>
        </div>
      </Card.Body>
</Card>
<Card className='custom-card-event'>
      <Card.Body>
        <Card.Title>Jul 28, 2024</Card.Title>
        <div style={{display:"flex",flexWrap:"nowrap",justifyContent:"space-between",flexDirection:"row"}}>
        <Card.Text>Multivariate Data Analysis using R and Python</Card.Text>
        <Card.Link href={pdf} target="_blank" className="custom-link2" >Read more</Card.Link>
        </div>
      </Card.Body>
</Card>
<Card className='custom-card-event'>
      <Card.Body>
        <Card.Title>Jul 27, 2024</Card.Title>
        <div style={{display:"flex",flexWrap:"nowrap",justifyContent:"space-between",flexDirection:"row"}}>
        <Card.Text>Sufi Culture in the Indus Valley The Making of a Vernacular Cosmopolitan Paradigm</Card.Text>
        <Card.Link href={pdf} target="_blank"className="custom-link2" >Read more</Card.Link>
        </div>
      </Card.Body>
</Card>
<Card className='custom-card-event'>
      <Card.Body>
        <Card.Title>Sep 01, 2024</Card.Title>
        <div style={{display:"flex",flexWrap:"nowrap",justifyContent:"space-between",flexDirection:"row"}}>
        <Card.Text>Fundamental Principles of Indoor Environmental Quality</Card.Text>
        <Card.Link href={pdf} target="_blank" className="custom-link2" >Read more</Card.Link>
        </div>
      </Card.Body>
</Card>
</div>

<div className='marquee'>

<marquee style={{margin:"40px"}}>
	The IKS Calender of 2024 (India of Ages), purported to be published by the Indian Institute of Technology Kharagpur, presently under circulation in Social Media is NOT an officially published calender of IIT Kharagpur. 

	Accordingly, the Institute, or any of its authorities, owe no responsibility whatsoever for the veracity of its contents or any other issues arising therefrom. The Institute bears No Knowledge of the content that is represented in individual capacity. &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; <a href="https://mrcet.com/pdf/eceevents/4.pdf" target="_blank">Click here for 69th Convocation</a>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; <a href="https://www.jainuniversity.ac.in/pdf/lumiere-2K18-fest.pdf" target="_blank">Terms and condition.</a>
</marquee>

</div>
    
    </div>   
    
  );
}

export default UniversityComponent