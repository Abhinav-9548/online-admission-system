package com.cg.admission.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "branch")
public class Branch {
	@Id
	@SequenceGenerator(name = "branch_", initialValue = 101, allocationSize = 10)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "branch_")
	private Long branchId;
	
	@NotBlank(message = "Branch name cannot be blank")
    @Size(max = 50, message = "Branch name cannot exceed 50 characters")
	private String branchName;
	
	
	@NotBlank(message = "Branch description cannot be blank")
    @Size(max = 255, message = "Branch description cannot exceed 255 characters")
	private String branchDescription;
}
