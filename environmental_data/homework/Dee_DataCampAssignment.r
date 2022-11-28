#Section 1

a <- "Stella"

b1 <- 45.6

b2 <- "45.6"

c1 <- c(0, 1, 2, 3)

#Q1: character

#Q2: numeric

#Q3: character

#Q4: You get an error because b2 is non-numeric and as a consequence can't be added to a number. 

#Q5: Yes, they are both numeric vectors, just b1 has only one element and c1 has three. 

#Q6: b1 gets added to each number in c1. 

#Section 2

#Q7
v1 <- c(-2:2)

#Q8
v2 <- v1 * 3

#Q9
sum(v2)

#Q10

vec_4 <- c(1:12)
mat_1 <- matrix(vec_4, nrow = 3, byrow = TRUE)

#Q11

mat_2 <- matrix(vec_4, nrow = 3, byrow = FALSE)

#Q12

one <- 5.2
two <- "five point two"
three <- c(0:5)
my_list_1 <- list(one, two, three)
names(my_list_1) <- c("one", "two","three")

#Q13
my_list_1[3]

#Q14
my_list_1$one

#Q15
my_bool_vec <- my_vec == 3

#Q16
my_vec[my_bool_vec]