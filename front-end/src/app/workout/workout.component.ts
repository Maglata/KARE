import { Component, Input } from '@angular/core';
import { PostDataService } from '../../services/post-data.service';

@Component({
  selector: 'app-workout',
  templateUrl: './workout.component.html',
  styleUrls: ['./workout.component.css']
})
export class WorkoutComponent {
  @Input() workout: any = {};

  constructor(private postDataService: PostDataService) {}

  deleteWorkout() {
    const workoutId = this.workout.id; // Assuming 'id' is the property that holds the workout's unique identifier
    this.postDataService.deleteWorkout(workoutId).subscribe(
      () => {
        // Reload the page after successful deletion
        window.location.reload();
      },
      (error) => {
        console.error('Error deleting workout:', error);
        // Handle error (e.g., display an error message to the user)
      }
    );
  }
  completeWorkout() {
    const workoutId = this.workout.id;
    this.postDataService.completeWorkout(workoutId).subscribe(
      (response) => {
        alert("Good job for completing the workout!")
      },
      (error) => {
        alert('Error Completing workout:' + error);
      }
    );
  }
}
