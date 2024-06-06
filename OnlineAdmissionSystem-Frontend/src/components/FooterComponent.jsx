import React from "react";
import {
  MDBFooter,
  MDBContainer,
  MDBIcon,
  MDBCol,
  MDBRow,
} from "mdb-react-ui-kit";

function Footer() {
  return (
  

    <div className="Foot text-center" color="light" bgColor="dark ">
      <MDBContainer className="p-4">
        <section className="">
          <MDBRow>
            <MDBCol xl="6" className="mx-auto mb-0">
              <h6 className="text-uppercase fw-bold mb-4">
                Online Admission System
              </h6>

              <p>
              © 1961-2024 Online Admission System
              </p>
            </MDBCol>
          </MDBRow>
        </section>
      </MDBContainer>

      {/* <div className='text-center p-3' style={{ backgroundColor: 'rgba(0, 0, 0, 0.2)' }}>
                © 2020 Copyright: <br />
                <a className='text-white'>
                    GreenGrocers.com
                </a>
            </div> */}
    </div>
  );
}

export default Footer;
