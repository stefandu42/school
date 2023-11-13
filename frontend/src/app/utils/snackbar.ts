import { MatSnackBar } from '@angular/material/snack-bar';

export function openSnackBar(
  snackBar: MatSnackBar,
  message: string,
  action: string
) {
  snackBar.open(message, action, {
    duration: 5000,
  });
}
