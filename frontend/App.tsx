import React from 'react';
import {NavigationContainer} from '@react-navigation/native';
// Stack Navigator
import {createNativeStackNavigator} from '@react-navigation/native-stack';

import StudentLogin from './screens/StudentLogin.tsx';
import CreateAccount from './screens/CreateAccount.tsx';
import StudentProfile from './screens/StudentProfile.tsx';
import EditStudentProfile from './screens/EditStudentProfile.tsx';
import ExploreCourses from './screens/ExploreCourses.tsx';
import CourseEnroll from './screens/CourseEnroll.tsx';
import FacultyTALogin from './screens/FacultyTALogin.tsx';
import FacultyTADashboard from './screens/FacultyTADashboard.tsx';
import StartNewCourse from './screens/StartNewCourse.tsx';
import CourseDetails from './screens/CourseDetails.tsx';
import CourseDetailsEditOrView from './screens/CourseDetailsEditOrView.tsx';
import AddTopic from './screens/AddTopic.tsx';
import ViewTopics from './screens/ViewTopics.tsx';
import Topic from './screens/Topic.tsx';
import AddVideo from './screens/AddVideo.tsx';
import AddMaterial from './screens/AddMaterial.tsx';
import AddAssignment from './screens/AddAssignment.tsx';
import StudentDashboard from './screens/StudentDashboard.tsx';
import FacultyProfile from './screens/FacultyProfile.tsx';
import Notifications from './screens/Notifications.tsx';
import NotificationDetails from './screens/NotificationDetails.tsx';

export type RootStackParamList = {
  StudentLogin: undefined;
  CreateAccount: undefined;
  StudentProfile: undefined;
  EditStudentProfile: undefined;
  ExploreCourses: undefined;
  CourseEnroll: {courseId: number};
  FacultyTALogin: undefined;
  FacultyTADashboard: undefined;
  StartNewCourse: undefined;
  CourseDetails: {courseId: number};
  CourseDetailsEditOrView: {courseId: number};
  AddTopic: {courseId: number};
  ViewTopics: {courseId: number};
  Topic: {courseId: number; topicId: number};
  AddVideo: {courseId: number};
  AddMaterial: {courseId: number};
  AddAssignment: {courseId: number};
  StudentDashboard: undefined;
  FacultyProfile: undefined;
  Notifications: undefined;
  NotificationDetails: {notificationId: number};
};

// Create stack navigator
const Stack = createNativeStackNavigator<RootStackParamList>();

const App = () => {
  return (
    <NavigationContainer style={{fontFamily: 'arial'}}>
      <Stack.Navigator initialRouteName="Notifications">
        <Stack.Screen
          name="StudentLogin"
          component={StudentLogin}
          options={{headerShown: false}}
        />
        <Stack.Screen
          name="CreateAccount"
          component={CreateAccount}
          options={{headerShown: false}}
        />
        <Stack.Screen
          name="StudentProfile"
          component={StudentProfile}
          options={{headerShown: false}}
        />
        <Stack.Screen
          name="EditStudentProfile"
          component={EditStudentProfile}
          options={{headerShown: false}}
        />
        <Stack.Screen
          name="ExploreCourses"
          component={ExploreCourses}
          options={{headerShown: false}}
        />
        <Stack.Screen
          name="CourseEnroll"
          component={CourseEnroll}
          options={{headerShown: false}}
        />
        <Stack.Screen
          name="FacultyTALogin"
          component={FacultyTALogin}
          options={{headerShown: false}}
        />
        <Stack.Screen
          name="FacultyTADashboard"
          component={FacultyTADashboard}
          options={{headerShown: false}}
        />
        <Stack.Screen
          name="StartNewCourse"
          component={StartNewCourse}
          options={{headerShown: false}}
        />
        <Stack.Screen
          name="CourseDetails"
          component={CourseDetails}
          options={{headerShown: false}}
        />
        <Stack.Screen
          name="CourseDetailsEditOrView"
          component={CourseDetailsEditOrView}
          options={{headerShown: false}}
        />
        <Stack.Screen
          name="AddTopic"
          component={AddTopic}
          options={{headerShown: false}}
        />
        <Stack.Screen
          name="ViewTopics"
          component={ViewTopics}
          options={{headerShown: false}}
        />
        <Stack.Screen
          name="Topic"
          component={Topic}
          options={{headerShown: false}}
        />
        <Stack.Screen
          name="AddVideo"
          component={AddVideo}
          options={{headerShown: false}}
        />
        <Stack.Screen
          name="AddMaterial"
          component={AddMaterial}
          options={{headerShown: false}}
        />
        <Stack.Screen
          name="AddAssignment"
          component={AddAssignment}
          options={{headerShown: false}}
        />
        <Stack.Screen
          name="StudentDashboard"
          component={StudentDashboard}
          options={{headerShown: false}}
        />
        <Stack.Screen
          name="FacultyProfile"
          component={FacultyProfile}
          options={{headerShown: false}}
        />
        <Stack.Screen
          name="Notifications"
          component={Notifications}
          options={{headerShown: false}}
        />
        <Stack.Screen
          name="NotificationDetails"
          component={NotificationDetails}
          options={{headerShown: false}}
        />
      </Stack.Navigator>
    </NavigationContainer>
  );
};

export default App;
