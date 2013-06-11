# Jigglypuff
Enabling Secure Keyless Acoustic Communication for Smartphones

![Copy Right:http://userserve-ak.last.fm/serve/_/36166979/Jigglypuff++Seduction.gif](http://userserve-ak.last.fm/serve/_/36166979/Jigglypuff++Seduction.gif)

## Proposal
==========

### 1. Demonstrate encode function by transferring text to voice frequency (9,000~10,000 Hz).

In this process, we use two major techniques to implement transition, **FSK** and **ECC**. 

Frequency shift keying (FSK) is a frequency modulation scheme in which digital information is transmitted through discrete frequency changes of a carrier wave. Normally, the transmitted audio alternates between two tones: one , the "mark", represents a binary one, the other, the "space", represents a binary zero.
	
Error correction code (ECC) is a systematic way of building codes that could detect and correct multiple random symbols. It viewed as cyclic BCH codes, where encoding symbols are derived from the coefficients of a polynomial constructed by multiplying p(x) with a cyclic generator polynomial, which gives rise to efficient decoding algorithms.

### 2.  Demonstrate decode function by transferring voice frequency to text.



### 3.  Do the test on smart phones. To see if we can get the right text by choosing a specific voice frequency (handled by FSK).



### 4.  Testing if we can use FSK algorithm to transfer any text information to the voice frequency, and vice versa.



### 5. By adding the white noise, our group implement security function and test the correctness.

The applications of **accelerometer and distance detector** determine: 

1. the receiver generates jamming signal;  
2. sender sends information to receiver;
3. after receiver gets the mixed signal, it will remove the jamming signal.

## Team Member
==========
Chen Si

Wang Jun

Tu Yujin
