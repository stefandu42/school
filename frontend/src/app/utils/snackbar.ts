import { MatSnackBar } from '@angular/material/snack-bar';

/**
 * Opens a snack bar notification with the provided message and action.
 *
 * @param snackBar - The MatSnackBar service used to open the snack bar.
 * @param message - The message to be displayed in the snack bar.
 * @param action - The action text to be displayed in the snack bar.
 */
export function openSnackBar(
  snackBar: MatSnackBar,
  message: string,
  action: string
) {
  snackBar.open(message, action, {
    duration: 5000,
  });
}
