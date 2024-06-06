import React from 'react'
import { Container, Row, Col, Card } from 'react-bootstrap';
const AboutComponent = () => {
  return (
    <>
    
    <Container className="about">
      <Row>
        <Col>
          <h1 className="text-center mb-5">About Us</h1>
        </Col>
      </Row>
      <Row>
        <Col md={6} className="mb-4">
          <Card className="h-100">
            <Card.Body>
              <Card.Title>Our Mission</Card.Title>
              <Card.Text>
              Our mission is to provide a seamless and accessible online admission process for students worldwide. We aim to simplify the application journey, making it efficient, transparent, and user-friendly, enabling students to focus on their educational goals without the stress of complex admission procedures.
              </Card.Text>
            </Card.Body>
          </Card>
        </Col>
        <Col md={6} className="mb-4">
          <Card className="h-100">
            <Card.Body>
              <Card.Title>Our Vision</Card.Title>
              <Card.Text>
              Our vision is to revolutionize the way educational institutions handle admissions by harnessing the power of technology. We strive to become the leading platform for online admissions, known for our innovation, reliability, and commitment to student success. We envision a future where applying to educational programs is streamlined and straightforward, allowing students to reach their potential with ease.              </Card.Text>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
    
    </>
  )
}

export default AboutComponent