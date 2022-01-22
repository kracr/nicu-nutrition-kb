package com.inicu.postgres.utility;

public class InterpolationUtilities {

	public double poly_interpolate(double[] dataX, double[] dataY, double x, int power) {
		int xIndex = 0;
		while (xIndex < dataX.length - (1 + power + (dataX.length - 1) % power) && dataX[xIndex + power] < x) {
			xIndex += power;
		}

		double matrix[][] = new double[power + 1][power + 2];
		for (int i = 0; i < power + 1; ++i) {
			for (int j = 0; j < power; ++j) {
				matrix[i][j] = Math.pow(dataX[xIndex + i], (power - j));
			}
			matrix[i][power] = 1;
			matrix[i][power + 1] = dataY[xIndex + i];
		}
		double[] coefficients = lin_solve(matrix);
		double answer = 0;
		for (int i = 0; i < coefficients.length; ++i) {
			answer += coefficients[i] * Math.pow(x, (power - i));
		}
		return answer;
	}

	private double[] lin_solve(double[][] matrix) {
		double[] results = new double[matrix.length];
		int[] order = new int[matrix.length];
		for (int i = 0; i < order.length; ++i) {
			order[i] = i;
		}
		for (int i = 0; i < matrix.length; ++i) {
			// partial pivot
			int maxIndex = i;
			for (int j = i + 1; j < matrix.length; ++j) {
				if (Math.abs(matrix[maxIndex][i]) < Math.abs(matrix[j][i])) {
					maxIndex = j;
				}
			}
			if (maxIndex != i) {
				// swap order
				{
					int temp = order[i];
					order[i] = order[maxIndex];
					order[maxIndex] = temp;
				}
				// swap matrix
				for (int j = 0; j < matrix[0].length; ++j) {
					double temp = matrix[i][j];
					matrix[i][j] = matrix[maxIndex][j];
					matrix[maxIndex][j] = temp;
				}
			}
			/*
			 * if (Math.abs(matrix[i][i]) < 1e-15) { throw new RuntimeException(
			 * "Singularity detected"); }
			 */
			for (int j = i + 1; j < matrix.length; ++j) {
				double factor = matrix[j][i] / matrix[i][i];
				for (int k = i; k < matrix[0].length; ++k) {
					matrix[j][k] -= matrix[i][k] * factor;
				}
			}
		}
		for (int i = matrix.length - 1; i >= 0; --i) {
			// back substitute
			results[i] = matrix[i][matrix.length];
			for (int j = i + 1; j < matrix.length; ++j) {
				results[i] -= results[j] * matrix[i][j];
			}
			results[i] /= matrix[i][i];
		}
		double[] correctResults = new double[results.length];

		for (int i = 0; i < order.length; ++i) {
			// switch the order around back to the original order
			correctResults[order[i]] = results[i];
		}

		return results;
	}
}
