import React from 'react'
import { Container, Row, Col } from 'react-bootstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFacebook, faTwitter, faInstagram, faLinkedin } from '@fortawesome/free-brands-svg-icons';

const ContactComponent = () => {
  return (
    <>   
    <Container className="contact-container">
      <h1 className="text-center mb-5">Contact Us</h1>
      <Row className="justify-content-center">
        <Col md={6}>
          <p>We would love to hear from you. Reach out to us through any of the following channels:</p>
          <ul className="social-media-list">
            <li>
              <a href="https://www.facebook.com">
                <FontAwesomeIcon icon={faFacebook} size="2x" />
              </a>
            </li>
            <li>
              <a href="https://www.twitter.com">
                <FontAwesomeIcon icon={faTwitter} size="2x" />
              </a>
            </li>
            <li>
              <a href="https://www.instagram.com">
                <FontAwesomeIcon icon={faInstagram} size="2x" />
              </a>
            </li>
            <li>
              <a href="https://www.linkedin.com">
                <FontAwesomeIcon icon={faLinkedin} size="2x" />
              </a>
            </li>
          </ul>
        </Col>
      </Row>
    </Container>
    
    </>
  )
}

export default ContactComponent