package tradingcarbon.my_app.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tradingcarbon.my_app.model.ProjectDTO;
import tradingcarbon.my_app.service.ProjectService;
import tradingcarbon.my_app.util.ReferencedException;
import tradingcarbon.my_app.util.ReferencedWarning;


@RestController
@RequestMapping(value = "/api/projects", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjectResource {

    private final ProjectService projectService;

    public ProjectResource(final ProjectService projectService) {
        
    }

    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
        return ResponseEntity.ok(projectService.findAll());
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDTO> getProject(
            @PathVariable(name = "projectId") final UUID projectId) {
        return ResponseEntity.ok(projectService.get(projectId));
    }


    @DeleteMapping("/{projectId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteProject(
            @PathVariable(name = "projectId") final UUID projectId) {
        final ReferencedWarning referencedWarning = projectService.getReferencedWarning(projectId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        projectService.delete(projectId);
        return ResponseEntity.noContent().build();
    }

}
