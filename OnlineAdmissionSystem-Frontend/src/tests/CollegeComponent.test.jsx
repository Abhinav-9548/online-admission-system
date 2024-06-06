import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import { vi, describe, it, expect, beforeEach } from 'vitest';
import CollegeComponent from '../components/CollegeComponent'; // Adjust the import path as needed
import { findCollegeByUniverisityId, getUniversityById } from '../Services/UniversityService';

// Mock the services
vi.mock('../Services/UniversityService', () => ({
  findCollegeByUniverisityId: vi.fn(),
  getUniversityById: vi.fn(),
}));

const mockNavigate = vi.fn();
vi.mock('react-router-dom', async (importOriginal) => {
  const actual = await importOriginal();
  return {
    ...actual,
    useNavigate: () => mockNavigate,
    useParams: () => ({ universityId: '1' }),
  };
});

describe('CollegeComponent', () => {
  beforeEach(() => {
    mockNavigate.mockReset();
  });

  it('renders colleges under the specified university', async () => {
    const mockColleges = [
      {
        collegeRegId: '1',
        collegeName: 'Engineering College',
        address: {
          addressId: '123',
          city: 'City A',
          state: 'State A',
          country: 'Country A',
          zipcode: '123456',
        },
        description: 'A description for Engineering College',
      },
      {
        collegeRegId: '2',
        collegeName: 'Medical College',
        address: {
          addressId: '456',
          city: 'City B',
          state: 'State B',
          country: 'Country B',
          zipcode: '654321',
        },
        description: 'A description for Medical College',
      },
    ];

    const mockUniversity = {
      name: 'Test University',
    };

    findCollegeByUniverisityId.mockResolvedValueOnce({ data: mockColleges });
    getUniversityById.mockResolvedValueOnce({ data: mockUniversity });

    render(
      <BrowserRouter>
        <CollegeComponent />
      </BrowserRouter>
    );

    await waitFor(() => {
      expect(screen.getByText(/Colleges under Test University/i)).toBeInTheDocument();
    });

    // Debug logs
    console.log(screen.debug());

    expect(screen.getByText(/Engineering College/i)).toBeInTheDocument();
    expect(screen.getByText(/A description for Engineering College/i)).toBeInTheDocument();
    expect(screen.getByText(/Medical College/i)).toBeInTheDocument();
    expect(screen.getByText(/A description for Medical College/i)).toBeInTheDocument();
  });

  it('renders no colleges found message when no colleges are available', async () => {
    findCollegeByUniverisityId.mockResolvedValueOnce({ data: [] });
    getUniversityById.mockResolvedValueOnce({ data: { name: 'Test University' } });

    render(
      <BrowserRouter>
        <CollegeComponent />
      </BrowserRouter>
    );

    await waitFor(() => {
      expect(screen.getByText('No College Found')).toBeInTheDocument();
    });
  });

  it('navigates to the program page on button click', async () => {
    const mockColleges = [
      {
        collegeRegId: '1',
        collegeName: 'Engineering College',
        address: {
          addressId: '123',
          city: 'City A',
          state: 'State A',
          country: 'Country A',
          zipcode: '123456',
        },
        description: 'A description for Engineering College',
      },
    ];

    findCollegeByUniverisityId.mockResolvedValueOnce({ data: mockColleges });
    getUniversityById.mockResolvedValueOnce({ data: { name: 'Test University' } });

    render(
      <BrowserRouter>
        <CollegeComponent />
      </BrowserRouter>
    );

    await waitFor(() => {
      expect(screen.getByText(/Engineering College/i)).toBeInTheDocument();
    });

    // Debug logs
    console.log(screen.debug());

    const programButton = screen.getByRole('button', { name: /Programs offered/i });
    fireEvent.click(programButton);

    expect(mockNavigate).toHaveBeenCalledWith('/program/1');
  });
});
