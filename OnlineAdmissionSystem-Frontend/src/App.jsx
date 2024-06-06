import './App.css'
import './UserDashboard.css'
import './Form.css'
import './AdmissionConfirmation.css'
import './AdminDashboard.css'
import {BrowserRouter,Routes,Route} from 'react-router-dom'
import FooterComponent from './components/FooterComponent'
import HeaderComponent from './components/HeaderComponent'
import UniversityComponent from './components/UniversityComponent'
import UniversityList from './components/UniversityList'
import AboutComponent from './components/AboutComponent'
import ContactComponent from './components/ContactComponent'
import AdminUniversityComponent from './admincomponents/AdminUniversityComponent'
import CollegeComponent from './components/CollegeComponent'
import ProgramComponent from './components/ProgramComponent'
import CourseComponent from './components/CourseComponent'
import DashboardComponent from './components/DashboardComponent'
import ProgramScheduleCourse from './components/ProgramScheduleCourse'
import BranchComponent from './components/BranchComponent'
import ProgramScheduleBranch from './components/ProgramScheduleBranch'
import ApplyComponent from './components/ApplyComponent'
import StudentAllApplication from './components/StudentAllApplication'
import PaymentComponent from './components/PaymentComponent'
import StudentApplication from './components/StudentApplication'
import StudentAdmissionComponent from './components/StudentAdmissionComponent'
import AdminUniversityList from './admincomponents/AdminUniversityList'
import AdminDashboard from './admincomponents/AdminDashboard'
import AdminAllApplication from './admincomponents/AdminAllApplication'
import AdminApplicationForm from './admincomponents/AdminApplicationForm'
import AdminAllAdmission from './admincomponents/AdminAllAdmission'
import AdminPayment from './admincomponents/AdminPayment'
import AdminAllProgramSchedule from './admincomponents/AdminAllProgramSchedule'
import AdminProgramScheduleComponent from './admincomponents/AdminProgramScheduleComponent'
import AdminProgramScheduleList from './admincomponents/AdminProgramScheduleList'
import AdminCollegeList from './admincomponents/AdminCollegeList'
import AdminCollegeComponent from './admincomponents/AdminCollegeComponent'
import AdminProgramList from './admincomponents/AdminProgramList'
import AdminProgramComponent from './admincomponents/AdminProgramComponent'
import AdminCourseList from './admincomponents/AdminCourseList'
import AdminCourseComponent from './admincomponents/AdminCourseComponent'
import AdminBranchList from './admincomponents/AdminBranchList'
import AdminBranchComponent from './admincomponents/AdminBranchComponent'
import Search from './components/Search'
import ProgramScheduledApply from './components/ProgramScheduleApply'



function App() {
  

  return (
    <div className='Main'>
    
      <BrowserRouter>
      <HeaderComponent/>
      <Routes>
        
        <Route path='/' element={<UniversityComponent/>}/>
        <Route path='/university-By-City/:city' element={<UniversityList/> }/>
        <Route path='/colleges/:universityId' element={<CollegeComponent/>}/>
        <Route path='/about' element={<AboutComponent/>}/>
        <Route path='/contact' element={<ContactComponent/>}/>
        <Route path='/admin' element={<AdminUniversityComponent/>}/>
        <Route path='/university-list' element={<AdminUniversityList/>}/>
        <Route path='/update-university/:uniId' element={<AdminUniversityComponent/>}/>
        <Route path='/add-university' element={<AdminUniversityComponent/>}/>
        <Route path='/program/:collegeId' element={<ProgramComponent/>}/>
        <Route path='/course/:programId' element={<CourseComponent/>}/>
       
        {/* <Route path="/application/:appId" element={<ApplicationComponent/>}></Route> */}
        <Route path='/programScheduledByCourse/:courseId' element={<ProgramScheduleCourse/>}/>
        <Route path='/branch/:courseId' element={<BranchComponent/>}/>
        <Route path='/programScheduledByBranch/:branchId' element={<ProgramScheduleBranch/>}/>
        <Route path='/program-schedule/:scheduleId' element={<ApplyComponent/>}/>
        
        

        <Route path='/dashboard' element={<DashboardComponent/>}/>        
        {/* <Route path='/student-dashBoard/:mail' element={ <StudentDashBoard/>}></Route> */}
        <Route path='/AllApplication/:emailId' element={ <StudentAllApplication/>}></Route>
        <Route path='/makePayment/:appId' element={ <PaymentComponent/>}></Route>
        <Route path='/application/:appId' element={<StudentApplication/>}></Route>
        <Route path='/application/:appId/payment' element={<PaymentComponent/>}></Route>
        <Route path='/admission/:appId' element={<StudentAdmissionComponent/>}></Route>

        <Route path='/admindashboard' element={<AdminDashboard/>}/>
        <Route path='/applications' element={<AdminAllApplication/>}/>
        <Route path='/admin-application/:appId' element={<AdminApplicationForm/>}/>
        <Route path='/admission' element={<AdminAllAdmission/>}/>
        <Route path='/payment' element={<AdminPayment/>}/>
        <Route path='/programschedule' element={<AdminAllProgramSchedule/>}/>
        <Route path='/addprogramScheduled' element={<AdminProgramScheduleComponent/>}/>
        <Route path='/updateprogramScheduled/:scheduleId' element={<AdminProgramScheduleList/>}/>


        <Route path="/college-info/:universityId" element={<AdminCollegeList/>}></Route>
        <Route path="/add-college/:universityId" element={<AdminCollegeComponent />}></Route>
        <Route path="/update-college/:universityId/:collegeId" element={<AdminCollegeComponent />}></Route>
        <Route path="/program-info/:collegeId" element={<AdminProgramList/>}></Route>
        <Route path="/add-program/:collegeId" element={<AdminProgramComponent/>}></Route>
          <Route path="/update-program/:collegeId/:programId1" element={<AdminProgramComponent/>}></Route>
          <Route path="/course-info/:collegeId/program/:programId" element={<AdminCourseList/>}></Route>
          <Route path="/add-course/:collegeId/:programId" element={<AdminCourseComponent/>}></Route>
          <Route path="/update-course/:collegeId/:programId/:courseId1" element={<AdminCourseComponent/>}></Route>
          <Route path="/branch-info/:collegeId/course/:courseId" element={<AdminBranchList/>}></Route>
          <Route path="/add-branch/:collegeId/:courseId" element={<AdminBranchComponent/>}></Route>
          <Route path="/update-branch/:collegeId/:courseId/:branchId1" element={<AdminBranchComponent/>}></Route>

          <Route path='/college-detail/:searchTerm' element={<Search/>}/>
          <Route path='/program-schedule-apply' element={<ProgramScheduledApply/>}/>



      </Routes>
      <FooterComponent/>
      </BrowserRouter>
    </div>
  )
}

export default App
