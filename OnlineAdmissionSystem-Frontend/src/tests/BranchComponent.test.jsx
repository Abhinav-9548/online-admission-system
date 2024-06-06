import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import { vi, describe, it, expect, beforeEach } from 'vitest';
import BranchComponent from '../components/BranchComponent'; // Adjust the import path as needed
import { getAllBranchesByCourseId } from '../Services/BranchService';

// Mock the service
vi.mock('../Services/BranchService', () => ({
  getAllBranchesByCourseId: vi.fn(),
}));

const mockNavigate = vi.fn();
vi.mock('react-router-dom', async (importOriginal) => {
  const actual = await importOriginal();
  return {
    ...actual,
    useNavigate: () => mockNavigate,
    useParams: () => ({ courseId: '1' }),
  };
});

describe('BranchComponent', () => {
  beforeEach(() => {
    mockNavigate.mockReset();
  });

  it('renders branch details when branches are available', async () => {
    const mockBranches = [
      {
        branchId: '1',
        branchName: 'Computer Science',
        branchDescription: 'Description for CS',
      },
      {
        branchId: '2',
        branchName: 'Mechanical Engineering',
        branchDescription: 'Description for ME',
      },
    ];

    getAllBranchesByCourseId.mockResolvedValueOnce({ data: mockBranches });

    render(
      <BrowserRouter>
        <BranchComponent />
      </BrowserRouter>
    );

    await waitFor(() => {
      expect(screen.getByText('Branch Details')).toBeInTheDocument();
    });

    expect(screen.getByText('Computer Science')).toBeInTheDocument();
    expect(screen.getByText('Description for CS')).toBeInTheDocument();
    expect(screen.getByText('Mechanical Engineering')).toBeInTheDocument();
    expect(screen.getByText('Description for ME')).toBeInTheDocument();
  });

  it('renders no branches found message when no branches are available', async () => {
    getAllBranchesByCourseId.mockResolvedValueOnce({ data: [] });

    render(
      <BrowserRouter>
        <BranchComponent />
      </BrowserRouter>
    );

    await waitFor(() => {
      expect(screen.getByText('No Branch Found')).toBeInTheDocument();
    });
  });

  it('navigates to programScheduledByBranch on button click', async () => {
    const mockBranches = [
      {
        branchId: '1',
        branchName: 'Computer Science',
        branchDescription: 'Description for CS',
      },
    ];

    getAllBranchesByCourseId.mockResolvedValueOnce({ data: mockBranches });

    render(
      <BrowserRouter>
        <BranchComponent />
      </BrowserRouter>
    );

    await waitFor(() => {
      expect(screen.getByText('Computer Science')).toBeInTheDocument();
    });

    const applyButton = screen.getByRole('button', { name: /Apply Now/i });
    fireEvent.click(applyButton);

    expect(mockNavigate).toHaveBeenCalledWith('/programScheduledByBranch/1');
  });
});
