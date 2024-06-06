import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
 
import { admissionById } from '../Services/AdmissionService';
import { getApplicationId } from '../Services/ApplicationService';
import { vi, describe, it, expect } from 'vitest';
import StudentAdmissionComponent from '../components/StudentAdmissionComponent';
 
// Mock the services
vi.mock('../Services/AdmissionService', () => ({
    admissionById: vi.fn(),
  }));
 
  vi.mock('../Services/ApplicationService', () => ({
    getApplicationId: vi.fn(),
  }));
 
  const mockNavigate = vi.fn();
  vi.mock('react-router-dom', async (importOriginal) => {
    const actual = await importOriginal();
    return {
      ...actual,
      useNavigate: () => mockNavigate,
      useParams: () => ({ appId: '123' }),
    };
  });
 
  describe('StudentAdmissionComponent', () => {
    it('renders admission details when admission status is "Accepted"', async () => {
      admissionById.mockResolvedValueOnce({ data: {
        admissionStatus: 'Accepted',
        emailId: 'test@example.com',
        college: { collegeName: 'Test College' },
        program: { programName: 'Test Program' },
        course: { courseName: 'Test Course' },
        year: 2024
      }});
      getApplicationId.mockResolvedValueOnce({ data: { applicationId: '456' }});
 
      render(
        <BrowserRouter>
          <StudentAdmissionComponent />
        </BrowserRouter>
      );
 
      await waitFor(() => {
        expect(screen.getByText('Admission confirmed successfully!')).toBeInTheDocument();
      });
 
      expect(screen.getByText('test@example.com')).toBeInTheDocument();
      expect(screen.getByText('Test College')).toBeInTheDocument();
      expect(screen.getByText('Test Program')).toBeInTheDocument();
      expect(screen.getByText('Test Course')).toBeInTheDocument();
      expect(screen.getByText('2024')).toBeInTheDocument();
    });
 
    it('renders payment button when admission status is not "Accepted"', async () => {
        admissionById.mockResolvedValueOnce({ data: { admissionStatus: 'Pending' } });
        getApplicationId.mockResolvedValueOnce({ data: { applicationId: '456' } });
   
        render(
          <BrowserRouter>
            <StudentAdmissionComponent />
          </BrowserRouter>
        );
   
        await waitFor(() => {
          expect(screen.getByText(/No Payment Found/i)).toBeInTheDocument();
        });
   
        const paymentButton = screen.getByRole('button', { name: /Make Payment/i });
        expect(paymentButton).toBeInTheDocument();
   
        fireEvent.click(paymentButton);
        expect(mockNavigate).toHaveBeenCalledWith('/application/123/payment');
      });
 
      it('renders application prompt when no application is found', async () => {
        admissionById.mockResolvedValueOnce({ data: {} });
        getApplicationId.mockResolvedValueOnce({ data: {} });
   
        render(
          <BrowserRouter>
            <StudentAdmissionComponent />
          </BrowserRouter>
        );
   
        await waitFor(() => {
          expect(screen.getByText((content, element) => {
            return element.tagName.toLowerCase() === 'h1' && content.includes('No Application Found');
          })).toBeInTheDocument();
        });
      });
 
    it('navigates back to dashboard on button click', async () => {
      admissionById.mockResolvedValueOnce({ data: { admissionStatus: 'Accepted' }});
      getApplicationId.mockResolvedValueOnce({ data: { applicationId: '456' }});
 
      render(
        <BrowserRouter>
          <StudentAdmissionComponent />
        </BrowserRouter>
      );
 
      await waitFor(() => {
        expect(screen.getByText('Admission confirmed successfully!')).toBeInTheDocument();
      });
 
      const goBackButton = screen.getByText('DashBoard');
      fireEvent.click(goBackButton);
      expect(mockNavigate).toHaveBeenCalledWith('/dashboard');
    });
  });