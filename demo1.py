import cv2
import numpy as np
from matplotlib import pyplot as plt


def magnitude(x, y):
    x_m = x * x
    y_m = y * y
    z_m = x_m + y_m
    return np.sqrt(z_m)


# Numpy 函数
img = cv2.imread("D:\\1.jpg", 0)
f = np.fft.fft2(img)
f_shift = np.fft.fftshift(f)
magnitude_spectrum1 = 20 * np.log10(np.abs(f_shift))

plt.subplot(121), plt.imshow(img, "gray")
plt.title("Input image"), plt.xticks([]), plt.yticks([])
plt.subplot(122), plt.imshow(magnitude_spectrum1, cmap="gray")
plt.title("Numpy fft2 image"), plt.xticks([]), plt.yticks([])
plt.savefig("D:\\2.png")
#plt.show()

# OpenCV 函数  貌似有点问题
# dft = cv2.dft(np.float32(img), flags=cv2.DFT_COMPLEX_OUTPUT)
# dft_shift = np.fft.fftshift(dft)
# magnitude_spectrum2 = 20 * np.log10(magnitude(dft_shift[:, :, 0], dft_shift[:, :, 1]))
#
# plt.subplot(121), plt.imshow(img, "gray")
# plt.title("Input image"), plt.xticks([]), plt.yticks([])
# plt.subplot(121), plt.imshow(magnitude_spectrum2, cmap="gray")
# plt.title("DFT image"), plt.xticks([]), plt.yticks([])
# plt.show()

